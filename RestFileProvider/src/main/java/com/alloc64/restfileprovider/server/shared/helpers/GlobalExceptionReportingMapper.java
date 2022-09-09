/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.helpers;

import com.alloc64.restfileprovider.server.shared.log.Log;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.EOFException;

@Provider
public class GlobalExceptionReportingMapper implements ExceptionMapper<Exception>
{
    @Override
    public Response toResponse(Exception e) {
        Response response;
        if (e instanceof WebApplicationException) {
            response = Response.fromResponse(((WebApplicationException) e).getResponse())
                    .entity(e.getMessage())
                    .build();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .type("text/plain").build();
        }

        if (!(e instanceof NotFoundException) && !(e instanceof EOFException)) {
            Log.ex(e);
        }

        return response;
    }
}
