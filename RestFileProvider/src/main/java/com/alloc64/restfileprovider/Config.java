/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider;

import com.alloc64.restfileprovider.server.shared.cnf.JsonConfig;

import java.io.File;

public class Config extends JsonConfig
{
    public static final Config shared = create("conf/rfp.json", Config.class);

    public static void bootstrap()
    {
    }

    @Override
    protected void initialize() throws Exception
    {
        super.initialize();

        if (BuildConfig.DEBUG)
        {
            Database db = new Database();
            db.setHost("host");
            db.setDatabase("fp-db");
            db.setUser("");
            db.setPassword("");
            db.setDaoDispatcherSync(false);
            db.setDaoDispatcherThreadsCount(1);
            setDb(db);
        }
    }

    private File storageFolder = new File("./tmp/fileProvider");
    private String authToken = "test-auth-token";
    private String publicHostUrl = "http://localhost:8081";
    private long maxAllowedStorageInBytes = 1024 * 1024 * 1024 * 5L; // 5GB of storage

    public File getStorageFolder()
    {
        return storageFolder;
    }

    public void setStorageFolder(File storageFolder)
    {
        this.storageFolder = storageFolder;
    }

    public String getAuthToken()
    {
        return authToken;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    public String getPublicHostUrl()
    {
        return publicHostUrl;
    }

    public void setPublicHostUrl(String publicHostUrl)
    {
        this.publicHostUrl = publicHostUrl;
    }

    public long getMaxAllowedStorageInBytes()
    {
        return maxAllowedStorageInBytes;
    }

    public void setMaxAllowedStorageInBytes(long maxAllowedStorageInBytes)
    {
        this.maxAllowedStorageInBytes = maxAllowedStorageInBytes;
    }
}
