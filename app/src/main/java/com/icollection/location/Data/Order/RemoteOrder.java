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

    public Flowable<String> getOrderData(@NonNull String PD_shopname_date) {

        //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/HPIC/orderno/PDHPIC20210315"
        String shopname = PD_shopname_date.substring(2,6);

        return Http
                .getInstance()
                .getApi()
                .getOrderData(shopname, PD_shopname_date);
    }
}
