package com.icollection.location.Data.Order;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OrderNo extends RealmObject implements Serializable {

    @PrimaryKey
    private String orderNo_id;

    public String getOrderNo_id() {
        return orderNo_id;
    }

    public void setOrderNo_id(String orderNo_id) {
        this.orderNo_id = orderNo_id;
    }

}
