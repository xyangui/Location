package com.icollection.location.Data.Order;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

public class OrderNoLocal {

    private static OrderNoLocal INSTANCE;
    private OrderNoLocal() {
    }
    public static OrderNoLocal getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderNoLocal();
        }
        return INSTANCE;
    }

    /**
     * 获取指定订单
     */
    public Flowable<OrderNo> getOrderNoList(String strOrderNo) {

        //Realm数据库
        Realm realm = Realm.getDefaultInstance();

        RealmResults<OrderNo> resultsAll = realm
                .where(OrderNo.class)
                .equalTo("orderNo_id", strOrderNo)
                .findAll();

        List<OrderNo> copied = realm.copyFromRealm(resultsAll);
        realm.close();

        if (copied.isEmpty()) {
            return Flowable.empty(); //必须为空，才能请求网络数据
        }

//        OrderNo orderNo = new OrderNo();
//        orderNo.setOrderNo_id(strOrderNo);

        return Flowable.just(copied.get(0));
    }

    /**
     * 保存订单
     */
    public void saveOrderNo(OrderNo orderNo) {

        //objects=null;或者为null，都不crash
        checkNotNull(orderNo);

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();   //开启事务
        realm.insertOrUpdate(orderNo);
        realm.commitTransaction();  //提交事务

        realm.close();
    }

}
