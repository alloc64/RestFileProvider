/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.helpers;

import com.alloc64.restfileprovider.server.shared.log.Log;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.logging.Level;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;

public class HttpListener {
    public HttpServer start(URI uri, Callable<ResourceConfig> c) throws Exception {
        ResourceConfig resourceConfig = c.call();
        resourceConfig.register(GlobalExceptionReportingMapper.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false);

        server.getServerConfiguration().setDefaultErrorPageGenerator((request, status, reasonPhrase, description, e) ->
        {
            Log.ex(e);
            return null;
        });

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        server.start();

        fixJerseyLogging();

        return server;
    }

    private void fixJerseyLogging() {
        java.util.logging.Logger l = java.util.logging.Logger.getLogger("org.glassfish.grizzly.http.server.HttpHandler");

        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);

    }
}
