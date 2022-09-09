/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider;

import com.alloc64.restfileprovider.dao.RepositoryFactory;
import com.alloc64.restfileprovider.handler.FileProviderApiHandler;
import com.alloc64.restfileprovider.handler.PublicDownloadApiHandler;
import com.alloc64.restfileprovider.provider.AvailableFileProvider;
import com.alloc64.restfileprovider.server.shared.helpers.HttpListener;
import com.alloc64.restfileprovider.server.shared.log.Log;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.security.Security;

public class RestFileProvider
{
    private static RestFileProvider instance;

    public static synchronized RestFileProvider get()
    {
        if (instance == null)
            instance = new RestFileProvider();

        return instance;
    }

    private RepositoryFactory repositoryFactory;
    private AvailableFileProvider availableFileProvider;

    public RepositoryFactory getRepositoryFactory()
    {
        return repositoryFactory;
    }

    public AvailableFileProvider getAvailableFileProvider()
    {
        return availableFileProvider;
    }

    static
    {
        Security.addProvider(new BouncyCastleProvider());

        Log.initialize("rfp");
        Config.bootstrap();

        Log.i("Version: " + BuildConfig.VERSION);
    }

    public RestFileProvider()
    {
        super();

        this.repositoryFactory = new RepositoryFactory();
        this.availableFileProvider = new AvailableFileProvider();
    }

    public void start()
    {
        try
        {
            new HttpListener().start(URI.create("http://0.0.0.0:8081/"), () ->
                    new ResourceConfig()
                            .register(MultiPartFeature.class)
                            .register(PublicDownloadApiHandler.class)
                            .register(FileProviderApiHandler.class));

            Thread.currentThread().join();
        }
        catch (Exception e)
        {
            Log.ex(e);
        }
    }
}
