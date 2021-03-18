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
    private final OrderNoLocal OrderNo_Local;

    @Nullable
    private List<OrderNo> listOrderNo;

    private OrderNoRepository() {
        this.OrderNo_Remote = RemoteOrder.getInstance();
        this.OrderNo_Local = OrderNoLocal.getInstance();

        listOrderNo = new ArrayList<>();
    }

//    public Flowable<List<OrderNo>> getOrderNo(@NonNull String orderNo) {
//
//        /**
//         * Respond immediately with cache if available and not dirty
//         */
//        if (!listOrderNo.isEmpty()) {
//            return Flowable
//                    .fromIterable(listOrderNo)
//                    //.filter(category -> Integer.valueOf(category.getCollection_id()) == collectionId)
//                    .toList()
//                    .toFlowable();
//        }
//
//        /**
//         * Query the local storage if available. If not, query the network.
//         */
//        return Flowable
//                .concat(getAndCacheLocalData(collectionId),
//                        getAndSaveRemoteData(collectionId)
//                ).firstOrError()    //firstElement()为空时不抛出异常，firstOrError()为空时抛出异常
//                .toFlowable();
//    }

}
