/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.cnf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.alloc64.restfileprovider.server.shared.log.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class JsonConfig
{
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static class Database
    {
        private String host;
        private String database;
        private String user;
        private String password;

        private Boolean daoDispatcherSync;
        private Integer daoDispatcherThreadsCount;

        public Database()
        {
        }

        @Deprecated
        public Database(String host, String database, String user, String password, boolean daoDispatcherSync, int daoDispatcherThreadsCount)
        {
            this.host = host;
            this.database = database;
            this.user = user;
            this.password = password;
            this.daoDispatcherSync = daoDispatcherSync;
            this.daoDispatcherThreadsCount = daoDispatcherThreadsCount;
        }

        public String getHost()
        {
            if(host == null)
                return "";

            return host;
        }

        public void setHost(String host)
        {
            this.host = host;
        }

        public String getDatabase()
        {
            if(database == null)
                return "";

            return database;
        }

        public void setDatabase(String database)
        {
            this.database = database;
        }

        public String getUser()
        {
            if(user == null)
                return "";

            return user;
        }

        public void setUser(String user)
        {
            this.user = user;
        }

        public String getPassword()
        {
            if(password == null)
                return "";

            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }

        public boolean isDaoDispatcherSync()
        {
            if(daoDispatcherSync == null)
                daoDispatcherSync = false;

            return daoDispatcherSync;
        }

        public void setDaoDispatcherSync(boolean daoDispatcherSync)
        {
            this.daoDispatcherSync = daoDispatcherSync;
        }

        public int getDaoDispatcherThreadsCount()
        {
            if(daoDispatcherThreadsCount == null)
                daoDispatcherThreadsCount = 4;

            return daoDispatcherThreadsCount;
        }

        public void setDaoDispatcherThreadsCount(int daoDispatcherThreadsCount)
        {
            this.daoDispatcherThreadsCount = daoDispatcherThreadsCount;
        }

        public void validate() throws IllegalStateException
        {
            if(StringUtils.isEmpty(getHost()))
                throw new IllegalStateException("Database: host is mandatory");

            if(StringUtils.isEmpty(getDatabase()))
                throw new IllegalStateException("Database: database is mandatory");
        }

        @Override
        public String toString()
        {
            return "Database{" +
                    "host='" + host + '\'' +
                    ", database='" + database + '\'' +
                    ", user='" + user + '\'' +
                    ", password='" + password + '\'' +
                    ", daoDispatcherSync=" + daoDispatcherSync +
                    ", daoDispatcherThreadsCount=" + daoDispatcherThreadsCount +
                    '}';
        }
    }

    @JsonIgnore
    private File file;

    private Database db;

    public JsonConfig()
    {
    }

    public boolean hasDatabaseValues()
    {
        return db != null;
    }


    public Database getDb()
    {
        if(db == null)
            db = new Database();

        return db;
    }

    public void setDb(Database db)
    {
        this.db = db;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    protected void createIfNotExists(File file)
    {
        setFile(file);

        try
        {
            if (!file.exists())
            {
                save(file);
                Log.i("Created an empty config file at " + file);
            }
        }
        catch (Exception e)
        {
            Log.ex(e);
        }
    }

    private static synchronized <T extends JsonConfig> T from(File file, Class<T> clazz)
    {
        try
        {
            if (file.exists())
            {
                String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

                //Log.i("Config: " + data);

                T config = objectMapper.readValue(data, clazz);

                if (config != null)
                {
                    config.initialize();
                    config.setFile(file);

                    return config;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(2);
        }

        return null;
    }

    protected static <T extends JsonConfig> T create(String path, Class<T> clazz)
    {
        File file = new File(path);

        T config = from(file, clazz);

        if(config == null)
        {
            try
            {
                config = clazz.newInstance();
                config.initialize();
                config.createIfNotExists(file);
            }
            catch (Exception e)
            {
                Log.ex(e);
                System.exit(1);
            }
        }

        return config;
    }

    public void save() throws Exception
    {
        if(file != null && file.exists())
            save(file);
    }

    private void save(File file) throws Exception
    {
        FileUtils.write(file, objectMapper.writeValueAsString(this), StandardCharsets.UTF_8);
    }

    protected void initialize() throws Exception
    {
        if(db != null)
            db.validate();

    }
}
