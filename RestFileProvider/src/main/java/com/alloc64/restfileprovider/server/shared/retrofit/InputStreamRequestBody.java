/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.retrofit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamRequestBody extends RequestBody
{
    private final MediaType contentType;
    private final InputStream is;

    public InputStreamRequestBody(MediaType contentType, InputStream is)
    {
        if (is == null) throw new NullPointerException("is == null");
        this.contentType = contentType;
        this.is = is;
    }

    @Override
    public MediaType contentType()
    {
        return contentType;
    }

    @Override
    public long contentLength() throws IOException
    {
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException
    {
        try(Source source = Okio.source(is))
        {
            sink.writeAll(source);
        }
    }
}