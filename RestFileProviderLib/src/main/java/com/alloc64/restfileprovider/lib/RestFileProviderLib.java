/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.lib;

import com.alloc64.restfileprovider.lib.model.UploadResultEntity;
import com.google.gson.Gson;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class RestFileProviderLib implements RestFileProviderLibSchema
{
    private final RestFileProviderLibSchema service;

    public RestFileProviderLib(String baseUrl, String authToken)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.HOURS)
                .writeTimeout(1, TimeUnit.HOURS);

        OkHttpClient client = builder.addInterceptor(chain ->
        {
            Request.Builder req = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Basic " + authToken);

            return chain.proceed(req.build());
        }).build();

        Retrofit retrofit =  new Retrofit.Builder().baseUrl(baseUrl)
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        this.service = retrofit.create(RestFileProviderLibSchema.class);
    }

    public Call<UploadResultEntity> uploadFile(File file, String filename)
    {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", filename,
                RequestBody.create(MediaType.parse("application/octet-stream"), file)
        );

        return uploadFile(filePart);
    }

    @Override
    public Call<UploadResultEntity> uploadFile(MultipartBody.Part filePart)
    {
        return service.uploadFile(filePart);
    }

    @Override
    public Call<Void> deleteFile(String guid)
    {
        return service.deleteFile(guid);
    }
}
