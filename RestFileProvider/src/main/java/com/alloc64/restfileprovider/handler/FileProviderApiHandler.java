/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.handler;

import com.alloc64.restfileprovider.Config;
import com.alloc64.restfileprovider.dao.model.AvailableFileLinkAwareEntity;
import com.alloc64.restfileprovider.lib.model.UploadResultEntity;
import com.alloc64.restfileprovider.provider.AvailableFileProvider;
import com.alloc64.restfileprovider.server.shared.log.Log;
import com.alloc64.restfileprovider.server.shared.response.PaginatedResponse;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class FileProviderApiHandler
{
    @POST
    @Path("/file-provider/v1/api/upload/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@HeaderParam("Authorization") String auth, @FormDataParam("file") FormDataBodyPart filePart)
    {
        try
        {
            authenticate(auth);

            UploadResultEntity response = AvailableFileProvider
                    .get()
                    .upload(filePart);

            return Response.ok(response).build();
        }
        catch (WebApplicationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            Log.ex(e);
        }

        return Response.serverError().build();
    }

    @GET
    @Path("/file-provider/v1/api/download/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response download(@PathParam("guid") String guid)
    {
        return AvailableFileProvider.get().download(guid);
    }

    @GET
    @Path("/file-provider/v1/api/list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResponse<AvailableFileLinkAwareEntity> list(@HeaderParam("Authorization") String auth,
                                                                @QueryParam("offset") Long offset,
                                                                @QueryParam("limit") Long limit)
    {
        authenticate(auth);

        return AvailableFileProvider.get().list(offset, limit);
    }

    @POST
    @Path("/file-provider/v1/api/delete/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@HeaderParam("Authorization") String auth,
                           @PathParam("guid") String guid)
    {
        authenticate(auth);

        AvailableFileProvider.get().delete(guid);

        return Response.ok().build();
    }

    private void authenticate(String header) throws NotFoundException
    {
        if(!StringUtils.isEmpty(header))
        {
            String basicToken = "Basic ";

            int basicIndex = header.indexOf(basicToken);

            if(basicIndex != -1)
            {
                basicIndex += basicToken.length();
                header = header.substring(basicIndex);

                if(StringUtils.equals(Config.shared.getAuthToken(), header))
                    return;
            }
        }

        throw new ForbiddenException();
    }
}
