package com.icollection.location.Data.Cash;

import java.util.List;

import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalCash {

    private static LocalCash INSTANCE;
    private LocalCash() {
    }
    public static LocalCash getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalCash();
        }
        return INSTANCE;
    }

    /**
     * 获取所有
     * @return
     */
    public Flowable<List<Cash>> getCashList() {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Cash> resultsAll = realm
                .where(Cash.class)
                .findAll();

        List<Cash> copied = realm.copyFromRealm(resultsAll);

        realm.close();

        return Flowable
                .fromIterable(copied)
                .toList()
                .toFlowable();
    }

    /**
     * 保存
     */
    public void saveCash(Cash cash) {

        //objects=null;或者为null，都不crash
        checkNotNull(cash);

        if(cash.getId() == -1) {
            cash.setId(getNextId());
        }

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();   //开启事务
        realm.insertOrUpdate(cash);
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

            Number number = realm.where(Cash.class).max("id");

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
    public void delete(int cash_id) {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Cash> resultsAll = realm
                .where(Cash.class)
                .equalTo("id", cash_id)
                .findAll();

        realm.beginTransaction();   //开启事务
        resultsAll.deleteAllFromRealm();
        realm.commitTransaction();  //提交事务

        realm.close();
    }
}
