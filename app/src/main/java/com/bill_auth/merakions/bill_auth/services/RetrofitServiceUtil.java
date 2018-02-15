package com.bill_auth.merakions.bill_auth.services;

/**
 * Created by bajpa on 12-Jul-17.
 */


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andy on 1/25/2016.
 */
public class RetrofitServiceUtil {

    /*private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
*/
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    
    public static <S> S createCustomService(Retrofit customRetrofit, Class<S> serviceClass) {
        return customRetrofit.create(serviceClass);
    }
}