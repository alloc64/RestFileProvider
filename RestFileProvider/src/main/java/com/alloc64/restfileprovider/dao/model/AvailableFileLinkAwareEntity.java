/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.dao.model;

import com.alloc64.restfileprovider.server.shared.log.Log;

import org.apache.http.client.utils.URIBuilder;

public class AvailableFileLinkAwareEntity extends AvailableFileEntity
{
    private String link;

    public AvailableFileLinkAwareEntity()
    {
    }

    public AvailableFileLinkAwareEntity(AvailableFileEntity e)
    {
        super(e.getIdAvailableFile(), e.getGuid(), e.getName());
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public void createLink(String publicHostUrl)
    {
        try
        {
            URIBuilder uriBuilder = new URIBuilder(publicHostUrl);
            uriBuilder.setPath("/download/" + getGuid());

            setLink(uriBuilder.toString());
        }
        catch (Exception e)
        {
            Log.ex(e);
        }
    }
}
