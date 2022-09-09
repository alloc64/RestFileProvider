/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.dao.interfaces;

import com.alloc64.restfileprovider.dao.model.AvailableFileEntity;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface IAvailableFileDao
{
    @SqlUpdate("CREATE TABLE IF NOT EXISTS available_file (\n" +
            "  id_available_file integer PRIMARY KEY,\n" +
            "  guid varchar(128) DEFAULT NULL UNIQUE,\n" +
            "  name varchar(128) DEFAULT NULL\n" +
            ")")
    void create();

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO available_file (id_available_file, guid, name) " +
            "VALUES (:idAvailableFile, :guid, :name) " +
            "ON CONFLICT(guid) DO UPDATE SET " +
            "id_available_file = excluded.id_available_file, " +
            "guid = excluded.guid, " +
            "name = excluded.name")
    Long insert(@BindBean AvailableFileEntity entity);

    @SqlQuery("SELECT * FROM available_file WHERE guid = :guid LIMIT 1")
    AvailableFileEntity get(@Bind("guid") String guid);

    @SqlQuery("SELECT COUNT(id_available_file) FROM available_file")
    long getTotalCount();

    @SqlQuery("SELECT * FROM available_file ORDER BY id_available_file LIMIT :offset, :limit")
    List<AvailableFileEntity> list(@Bind("offset") Long offset, @Bind("limit") Long limit);

    @SqlUpdate("DELETE FROM available_file WHERE guid = :guid")
    void delete(@Bind("guid") String guid);
}
