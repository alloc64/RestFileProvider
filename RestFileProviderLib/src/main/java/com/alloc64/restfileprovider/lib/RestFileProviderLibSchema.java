/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.lib;

import com.alloc64.restfileprovider.lib.model.UploadResultEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestFileProviderLibSchema
{
    @Multipart
    @POST("/file-provider/v1/api/upload/file")
    Call<UploadResultEntity> uploadFile(@Part MultipartBody.Part filePart);

    @POST("/file-provider/v1/api/delete/{guid}")
    Call<Void> deleteFile(@Path("guid") String guid);
}
