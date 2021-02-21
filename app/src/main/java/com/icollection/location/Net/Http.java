package com.icollection.location.Net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Http {

    private static Http instance = null;
    public static Http getInstance() {
        if (instance == null) {
            instance = new Http();
        }
        return instance;
    }

    //接口地址
    private static final String API_URL = "http://approd9h4leb60v4olh1v.phonecollection.com.au/";
    public static final String API_URL_IMAGE = "https://app.meljianghu.com/storage/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private Api api;

    private Http() {

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

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

}

