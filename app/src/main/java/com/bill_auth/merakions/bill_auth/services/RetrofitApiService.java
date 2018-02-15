package com.bill_auth.merakions.bill_auth.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by bajpa on 12-Jul-17.
 */

public interface RetrofitApiService {


    @Streaming
    @GET
    Call<ResponseBody> downloadReport(@Url String downloadUrl);
}
