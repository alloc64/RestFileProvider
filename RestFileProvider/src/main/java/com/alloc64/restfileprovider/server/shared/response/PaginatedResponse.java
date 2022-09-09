/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.response;

import java.util.ArrayList;
import java.util.List;

public class PaginatedResponse<T>
{
    public interface ITotalCountCallback
    {
        long call();
    }

    public interface IGetResultCallback<T>
    {
        List<T> call(long offset, long limit);
    }

    public static class Builder<T>
    {
        private Long offset;
        private Long limit;

        private ITotalCountCallback totalCountCallback;
        private IGetResultCallback<T> resultCallback;

        public Builder(Long offset, Long limit)
        {
            this.offset = offset;
            this.limit = limit;
        }

        public Builder<T> totalCount(ITotalCountCallback totalCountCallback)
        {
            this.totalCountCallback = totalCountCallback;
            return this;
        }

        public Builder<T> result(IGetResultCallback<T> resultCallback)
        {
            this.resultCallback = resultCallback;
            return this;
        }

        public PaginatedResponse<T> build()
        {
            PaginatedResponse<T> result = new PaginatedResponse<>();

            long totalCount = this.totalCountCallback.call();

            if(offset == null || limit == null)
            {
                offset = 0L;
                limit = totalCount;
            }

            if(totalCount > 0)
                result.setItems(this.resultCallback.call(offset, limit));
            else
                result.setItems(new ArrayList<>());

            result.setTotalCount(totalCount);

            return result;
        }
    }

    private List<T> items;
    private long totalCount;

    public PaginatedResponse()
    {
    }

    public PaginatedResponse(PaginatedResponse<T> r)
    {
        this(r.getItems(), r.getTotalCount());
    }

    public PaginatedResponse(List<T> items, long totalCount)
    {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<T> getItems()
    {
        return items;
    }

    public void setItems(List<T> items)
    {
        this.items = items;
    }

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }

}
