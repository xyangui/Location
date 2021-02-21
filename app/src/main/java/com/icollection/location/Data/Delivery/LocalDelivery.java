package com.icollection.location.Data.Delivery;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalDelivery {

    private static LocalDelivery INSTANCE;
    private LocalDelivery() {
    }
    public static LocalDelivery getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDelivery();
        }
        return INSTANCE;
    }

    /**
     * 获取所有
     * @return
     */
    public Flowable<List<Delivery>> getDeliveryList() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Delivery> resultsAll = realm
                .where(Delivery.class)
                .findAll();

        List<Delivery> copied = realm.copyFromRealm(resultsAll);

        realm.close();

        return Flowable
                .fromIterable(copied)
                .toList()
                .toFlowable();
    }

    /**
     * 保存
     */
    public void saveDelivery(Delivery delivery) {

        //objects=null;或者为null，都不crash
        checkNotNull(delivery);

        if(delivery.getId() == -1) {
            delivery.setId(getNextId());
        }

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();   //开启事务
        realm.insertOrUpdate(delivery);
        realm.commitTransaction();  //提交事务

        realm.close();
    }

    /**
     * 主键自动递增
     * @return
     */
    private int getNextId() {
        try {
            Realm realm = Realm.getDefaultInstance();

            Number number = realm.where(Delivery.class).max("id");

            realm.close();

            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    /**
     * 删除
     */
    public void delete(int delivery_id) {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Delivery> resultsAll = realm
                .where(Delivery.class)
                .equalTo("id", delivery_id)
                .findAll();

        realm.beginTransaction();   //开启事务
        resultsAll.deleteAllFromRealm();
        realm.commitTransaction();  //提交事务

        realm.close();
    }
}

