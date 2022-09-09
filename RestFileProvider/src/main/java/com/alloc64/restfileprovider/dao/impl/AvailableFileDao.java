/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.dao.impl;

import com.alloc64.restfileprovider.dao.RepositoryFactory;
import com.alloc64.restfileprovider.dao.interfaces.IAvailableFileDao;
import com.alloc64.restfileprovider.dao.model.AvailableFileEntity;
import com.alloc64.restfileprovider.server.shared.dao.AbstractRepositoryFactory;
import com.alloc64.restfileprovider.server.shared.dao.impl.AbstractDao;

import java.util.List;

public class AvailableFileDao extends AbstractDao<RepositoryFactory> implements IAvailableFileDao
{
    public AvailableFileDao(RepositoryFactory repositoryFactory)
    {
        super(repositoryFactory);
        create();
    }

    @Override
    public void create()
    {
        repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, Void>) handle ->
                {
                    handle.create();
                    return null;
                });
    }

    @Override
    public Long insert(AvailableFileEntity entity)
    {
        return repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, Long>) handle -> handle.insert(entity));
    }

    @Override
    public AvailableFileEntity get(String guid)
    {
        return repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, AvailableFileEntity>) handle -> handle.get(guid));
    }

    @Override
    public long getTotalCount()
    {
        return repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, Long>) IAvailableFileDao::getTotalCount);
    }

    @Override
    public List<AvailableFileEntity> list(Long offset, Long limit)
    {
        return repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, List<AvailableFileEntity>>) handle -> handle.list(offset, limit));
    }

    @Override
    public void delete(String guid)
    {
        repositoryFactory.withHandle(IAvailableFileDao.class,
                (AbstractRepositoryFactory.Callback<IAvailableFileDao, Void>) handle ->
                {
                    handle.delete(guid);
                    return null;
                });
    }
}
