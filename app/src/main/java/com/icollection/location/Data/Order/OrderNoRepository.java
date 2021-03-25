package com.icollection.location.Data.Order;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

public class OrderNoRepository {
    @Nullable
    private static OrderNoRepository INSTANCE = null;

    public static OrderNoRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderNoRepository();
        }
        return INSTANCE;
    }

    @NonNull
    private final RemoteOrder OrderNo_Remote;
    @NonNull
    private final OrderNoLocal OrderNo_Local;//在数据库里的订单号，都是有数据的订单，没有数据的订单号不保存

    @Nullable
    private List<OrderNo> listOrderNo; //在数组里的订单号，都是有数据的订单，没有数据的订单号不保存

    private OrderNoRepository() {
        this.OrderNo_Remote = RemoteOrder.getInstance();
        this.OrderNo_Local = OrderNoLocal.getInstance();

        listOrderNo = new ArrayList<>();
    }

    /**
     * 订单为null，返回false，否则返回true
     * @param strOrderNo
     * @return
     */
    public Flowable<Boolean> getOrderNo(@NonNull String strOrderNo) {

        /**
         * Respond immediately with cache if available and not dirty
         */
        if (!listOrderNo.isEmpty()) {

            Boolean isFind = false;
            for(OrderNo orderNo : listOrderNo){
                if(orderNo.getOrderNo_id().equals(strOrderNo)){
                    isFind = true;
                }
            }

            if(isFind) {
                return Flowable.just(true);
            }
        }

        /**
         * Query the local storage if available. If not, query the network.
         */
        return Flowable
                .concat(getAndCacheLocalData(strOrderNo),
                        getAndSaveRemoteData(strOrderNo)
                ).firstOrError()    //firstElement()为空时不抛出异常，firstOrError()为空时抛出异常
                .toFlowable();
    }

    /**
     * 找到时返回true，否则返回Flowable.empty();
     * @param strOrderNo
     * @return
     */
    private Flowable<Boolean> getAndCacheLocalData(@NonNull String strOrderNo) {

        return OrderNo_Local
                .getOrderNoList(strOrderNo)
                .flatMap(object -> {

                    listOrderNo.add(object);

                    return Flowable.just(true);
                });
    }

    /**
     * 订单为null，返回false，否则返回true
     * @param strOrderNo
     * @return
     */
    private Flowable<Boolean> getAndSaveRemoteData(@NonNull String strOrderNo) {

        return OrderNo_Remote
                .getOrderData(strOrderNo)
                .flatMap(objects -> {

                    if(objects.size() == 1){  //[{"status":0,"code_product":"null".. 代表没有订单
                        if(objects.get(0).getStatus() == 0){
                            return Flowable.just(false);
                        }
                    }

                    OrderNo orderNo = new OrderNo();
                    orderNo.setOrderNo_id(strOrderNo);

                    OrderNo_Local.saveOrderNo(orderNo);
                    listOrderNo.add(orderNo);

                    return Flowable.just(true);
                });
    }

}
