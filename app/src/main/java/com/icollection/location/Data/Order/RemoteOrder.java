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

        //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/NLIC/orderno/PDNLIC20210621"
        String shopname = PD_shopname_date.substring(2,6);

        return Http
                .getInstance()
                .getApi()
                .getOrderData(shopname, PD_shopname_date);
    }

    public Flowable<List<OrderData>> getOrderData_test(@NonNull String PD_shopname_date) {

        //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/BSIC/orderno/PDBSIC20210419"
        String shopname = PD_shopname_date.substring(2,6);

        //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/HPPC/orderno/PDHPPC20160530"

        return Http
                .getInstance()
                .getApi()
                .getOrderData("HPPC", "PDHPPC20160530");
    }

    public Flowable<List<ResultFromSaveOneToDB>> saveOneToDB(@NonNull String PD_shopname_date,
                                                             @NonNull String bcode,
                                                             @NonNull int qty) {

        return Http
                .getInstance()
                .getApi()
                .saveOneToDB(PD_shopname_date, bcode, qty);
    }
}
