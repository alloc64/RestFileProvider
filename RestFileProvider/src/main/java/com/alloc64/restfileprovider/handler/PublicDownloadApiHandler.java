/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.handler;

import com.alloc64.restfileprovider.provider.AvailableFileProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PublicDownloadApiHandler
{
    @GET
    @Path("/download/{guid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response download(@PathParam("guid") String guid)
    {
        return AvailableFileProvider.get().download(guid);
    }
}
