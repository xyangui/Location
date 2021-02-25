package com.icollection.location.Net;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiIP {

    //"http://120.150.81.21:1180/jsonfunction/get-shop-stock/code/SPGLSCS21100"
    @GET("get-shop-stock/code/{bcode}")
    Flowable<String> getShopStock(@Path("bcode") String bcode);
}
