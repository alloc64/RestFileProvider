/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.dao.impl;

import com.alloc64.restfileprovider.server.shared.dao.AbstractRepositoryFactory;

public class AbstractDao<T extends AbstractRepositoryFactory>
{
	protected T repositoryFactory;

	public AbstractDao(T repositoryFactory)
	{
		this.repositoryFactory = repositoryFactory;
	}
}
