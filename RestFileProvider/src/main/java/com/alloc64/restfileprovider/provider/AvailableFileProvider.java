/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.provider;

import com.alloc64.restfileprovider.Config;
import com.alloc64.restfileprovider.RestFileProvider;
import com.alloc64.restfileprovider.dao.RepositoryFactory;
import com.alloc64.restfileprovider.dao.impl.AvailableFileDao;
import com.alloc64.restfileprovider.dao.model.AvailableFileEntity;
import com.alloc64.restfileprovider.dao.model.AvailableFileLinkAwareEntity;
import com.alloc64.restfileprovider.lib.model.UploadResultEntity;
import com.alloc64.restfileprovider.server.shared.helpers.MultipartHelper;
import com.alloc64.restfileprovider.server.shared.response.PaginatedResponse;
import com.alloc64.restfileprovider.server.shared.util.HashUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.stream.Collectors;

public class AvailableFileProvider
{
    public static AvailableFileProvider get()
    {
        return RestFileProvider.get().getAvailableFileProvider();
    }

    public UploadResultEntity upload(FormDataBodyPart filePart) throws Exception
    {
        UploadResultEntity uploadResult = new UploadResultEntity();

        try
        {
            String filename = MultipartHelper.getFilename(filePart);

            AvailableFileLinkAwareEntity e = new AvailableFileLinkAwareEntity();

            e.setGuid(UUID.randomUUID().toString());
            e.setName(filename);
            e.writeData(Config.shared.getStorageFolder(), filePart.getEntityAs(InputStream.class));

            File writtenFile = e.getFile(Config.shared.getStorageFolder());

            if (!hasEnoughSpace(writtenFile.length()))
            {
                writtenFile.delete();
                throw new WebApplicationException("Not enough space for storage. Delete some files and again later.", null, Response.Status.REQUEST_ENTITY_TOO_LARGE);
            }

            RepositoryFactory
                    .get()
                    .getAvailableFileDao()
                    .insert(e);

            e.createLink(Config.shared.getPublicHostUrl());

            uploadResult.setGuid(e.getGuid());
            uploadResult.setFilename(filename);
            uploadResult.setChecksum(
                    HashUtils.sha256Base64(e.getFile(Config.shared.getStorageFolder()))
            );
            uploadResult.setLink(e.getLink());
        }
        catch (Exception e)
        {
            uploadResult.setError(e.getMessage());
            throw e;
        }

        return uploadResult;
    }

    public Response download(String guid)
    {
        AvailableFileEntity availableFile = RepositoryFactory
                .get()
                .getAvailableFileDao()
                .get(guid);

        if (availableFile == null)
            throw new BadRequestException("File not found.");

        File file = availableFile.getFile(Config.shared.getStorageFolder());

        if (!file.exists())
            throw new BadRequestException("File does not exists.");

        return Response
                .ok((StreamingOutput) output -> IOUtils.copy(new FileInputStream(file), output))
                .type("application/octet-stream")
                .header("Content-Disposition", "attachment; filename=\"" + availableFile.getName() + "\"")
                .build();
    }

    public PaginatedResponse<AvailableFileLinkAwareEntity> list(Long offset, Long limit)
    {
        if (offset == null)
            offset = 0L;

        if (limit == null)
            limit = 20L;

        AvailableFileDao availableFileDao = RepositoryFactory.get()
                .getAvailableFileDao();

        return new PaginatedResponse.Builder<AvailableFileLinkAwareEntity>(offset, limit)
                .totalCount(availableFileDao::getTotalCount)
                .result((offset0, limit0) ->
                        availableFileDao.list(offset0, limit0).stream().map(r ->
                        {
                            AvailableFileLinkAwareEntity e = new AvailableFileLinkAwareEntity(r);
                            e.createLink(Config.shared.getPublicHostUrl());
                            return e;
                        }).collect(Collectors.toList()))
                .build();
    }

    public void delete(String guid)
    {
        AvailableFileDao availableFileDao = RepositoryFactory.get()
                .getAvailableFileDao();

        AvailableFileEntity availableFile = availableFileDao.get(guid);

        if (availableFile == null)
            return;

        availableFile
                .getFile(Config.shared.getStorageFolder())
                .delete();

        availableFileDao.delete(guid);
    }

    private boolean hasEnoughSpace(long size)
    {
        long remainingSpace = Math.min(
                Config.shared.getStorageFolder().getFreeSpace(),
                Config.shared.getMaxAllowedStorageInBytes()
        );

        return remainingSpace - size > 0;
    }
}
