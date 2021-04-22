package com.icollection.location.Data.Order;

import android.support.annotation.NonNull;

import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Net.Http;

import java.util.List;

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

    public Flowable<List<OrderData>> getOrderData(@NonNull String PD_shopname_date) {

        //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/BSIC/orderno/PDBSIC20210419"
        String shopname = PD_shopname_date.substring(2,6);

        return Http
                .getInstance()
                .getApi()
                .getOrderData(shopname, PD_shopname_date);
    }
}
