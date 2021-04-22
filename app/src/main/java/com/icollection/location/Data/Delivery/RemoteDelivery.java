package com.icollection.location.Data.Delivery;

import android.support.annotation.NonNull;

import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Net.Http;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Path;

public class RemoteDelivery {

    private static RemoteDelivery INSTANCE;
    private RemoteDelivery() {
    }
    public static RemoteDelivery getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDelivery();
        }
        return INSTANCE;
    }

    public Flowable<List<PartsRMAResult>> sendPartsRMAList(@NonNull String rma_list,
                                                           @NonNull String shop_stuff_password,
                                                           @NonNull String driver_password) {

        return Http
                .getInstance()
                .getApi()
                .sendPartsRMAList(rma_list, shop_stuff_password, driver_password);
    }
}
