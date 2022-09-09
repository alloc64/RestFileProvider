/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.alloc64.restfileprovider.Config;
import com.alloc64.restfileprovider.RestFileProvider;
import com.alloc64.restfileprovider.dao.impl.AvailableFileDao;
import com.alloc64.restfileprovider.dao.model.AvailableFileEntity;
import com.alloc64.restfileprovider.server.shared.cnf.JsonConfig;
import com.alloc64.restfileprovider.server.shared.dao.AbstractRepositoryFactory;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.sqlite3.SQLitePlugin;

public class RepositoryFactory extends AbstractRepositoryFactory
{
    public static RepositoryFactory get()
    {
        return RestFileProvider.get().getRepositoryFactory();
    }

    private AvailableFileDao availableFileDao;

    public AvailableFileDao getAvailableFileDao()
    {
        return availableFileDao;
    }

    public RepositoryFactory()
    {
        super(Config.shared.getDb());

        // entities
        dbi.registerRowMapper(BeanMapper.factory(AvailableFileEntity.class));

        this.availableFileDao = new AvailableFileDao(this);
    }

    @Override
    protected Jdbi onJdbiCreate(JsonConfig.Database db)
    {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:sqlite:conf/%s.db", db.getDatabase()));
        config.setMaximumPoolSize(10);

        config.setAutoCommit(false);
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");

        return Jdbi.create(
                new HikariDataSource(config)
        );
    }

    @Override
    protected void onJdbiCreated(Jdbi dbi)
    {
        super.onJdbiCreated(dbi);

        dbi.installPlugin(new SQLitePlugin());
    }
}

