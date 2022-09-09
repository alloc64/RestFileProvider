/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.helpers;

import com.alloc64.restfileprovider.server.shared.retrofit.InputStreamRequestBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MultipartHelper
{
    public static <T> T getEntity(FormDataBodyPart data, Class<T> clazz)
    {
        if(data == null)
            return null;

        data.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        return data.getValueAs(clazz);
    }

    public static MultipartBody.Part createPart(FormDataBodyPart part) throws UnsupportedEncodingException
    {
        return createPart(part.getName(), getFilename(part), part);
    }

    public static MultipartBody.Part createPart(String partName, String fileName, FormDataBodyPart part)
    {
        ContentDisposition disposition = part.getContentDisposition();
        okhttp3.MediaType mime = okhttp3.MediaType.parse(disposition.getType());

        RequestBody requestBody = new InputStreamRequestBody(mime, part.getEntityAs(InputStream.class));

        return MultipartBody.Part.createFormData(partName, fileName, requestBody);
    }

    public static String getFilename(FormDataBodyPart filePart) throws UnsupportedEncodingException
    {
        return URLDecoder.decode(filePart.getContentDisposition().getFileName(), "UTF-8");
    }
}
