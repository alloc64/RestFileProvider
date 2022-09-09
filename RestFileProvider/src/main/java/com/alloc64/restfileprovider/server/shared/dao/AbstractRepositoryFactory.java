/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.alloc64.restfileprovider.server.shared.cnf.JsonConfig;
import com.alloc64.restfileprovider.server.shared.log.Log;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class AbstractRepositoryFactory
{
	public interface Callback<T, U>
	{
		U use(T handle);
	}

	protected final Jdbi dbi;

	public AbstractRepositoryFactory(JsonConfig.Database db)
	{
		try
		{
			this.dbi = onJdbiCreate(db);

			onJdbiCreated(dbi);

			//dbi.registerArgument(new ValueEnumArgumentFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unable to connect to: " + db.getHost() + "/" + db.getDatabase(), e);
		}
	}

	protected Jdbi onJdbiCreate(JsonConfig.Database db)
    {
        Log.i("Connecting to database " + db.getHost() + "/" + db.getDatabase());

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(String.format("jdbc:mysql://%s/%s?useTimezone=true&serverTimezone=UTC", db.getHost(), db.getDatabase()));
        config.setUsername(db.getUser());
        config.setPassword(db.getPassword());
        config.setMaximumPoolSize(10);

        config.setAutoCommit(false);
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");

        return Jdbi.create(
                new HikariDataSource(config)
        );
    }

	protected void onJdbiCreated(Jdbi dbi)
	{
		dbi.installPlugin(new SqlObjectPlugin());
	}

	public <T extends Object, U> U withHandle(final Class clazz, final Callback<T, U> callback)
	{
		return dbi.withHandle(c -> {
			T attached = (T)c.attach(clazz);

			U result = null;
			if(callback != null)
				result = callback.use(attached);

			c.commit();

			return result;
		});
	}

	public Jdbi getDbi()
	{
		return dbi;
	}

	public Handle disposableHandle()
	{
		return dbi.open();
	}
}
