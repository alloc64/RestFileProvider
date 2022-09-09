/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.lib.model;

import org.apache.commons.lang3.StringUtils;

public class UploadResultEntity
{
    private String guid;
    private String filename;
    private String link;
    private String checksum;
    private String error;

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public void setChecksum(String checksum)
    {
        this.checksum = checksum;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public boolean isSuccessful()
    {
        return !StringUtils.isEmpty(getLink());
    }
}
