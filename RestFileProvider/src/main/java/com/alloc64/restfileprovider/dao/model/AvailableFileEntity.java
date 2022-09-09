/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

public class AvailableFileEntity
{
    private Long idAvailableFile;
    private String guid;
    private String name;

    public AvailableFileEntity()
    {

    }

    public AvailableFileEntity(Long idAvailableFile, String guid, String name)
    {
        this.idAvailableFile = idAvailableFile;
        this.guid = guid;
        this.name = name;
    }

    public Long getIdAvailableFile()
    {
        return idAvailableFile;
    }

    public void setIdAvailableFile(Long idAvailableFile)
    {
        this.idAvailableFile = idAvailableFile;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @JsonIgnore
    public File getFile(File parentFolder)
    {
        return new File(parentFolder, getGuid());
    }

    @JsonIgnore
    public void writeData(File parentFolder, InputStream is) throws Exception
    {
        File file = getFile(parentFolder);

        FileUtils.copyInputStreamToFile(is, file);
    }
}

