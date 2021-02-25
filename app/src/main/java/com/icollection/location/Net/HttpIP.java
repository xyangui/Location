package com.icollection.location.Net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpIP {

    private static HttpIP instance = null;
    public static HttpIP getInstance() {
        if (instance == null) {
            instance = new HttpIP();
        }
        return instance;
    }

    //接口地址
    private static final String API_URL = "http://120.150.81.21:1180/jsonfunction/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiIP api;

    private HttpIP() {

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create()) //添加接收字符串，不影响json的接收
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_URL)
                .build();

        api = retrofit.create(ApiIP.class);
    }

    public ApiIP getApi() {
        return api;
    }
}
