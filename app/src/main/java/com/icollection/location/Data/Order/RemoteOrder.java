package com.icollection.location.Data.Order;

import android.support.annotation.NonNull;

import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Net.Http;

import io.reactivex.Flowable;

public class RemoteOrder {

    private static RemoteOrder INSTANCE;
    private RemoteOrder() {
    }
    public static RemoteOrder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteOrder();
        }
        return INSTANCE;
    }

    public Flowable<String> getOrderData(@NonNull String shopname, @NonNull String PD_shopname_date) {

        return Http
                .getInstance()
                .getApi()
                .getOrderData(shopname, PD_shopname_date);
    }
}
