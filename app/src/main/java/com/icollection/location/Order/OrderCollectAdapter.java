package com.icollection.location.Order;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Location.LocationGet;
import com.icollection.location.Data.Order.OrderData;
import com.icollection.location.R;

import java.util.List;

public class OrderCollectAdapter extends BaseQuickAdapter<OrderData, BaseViewHolder> {

    private Context context;

    public OrderCollectAdapter(List<OrderData> data, Context context) {
        super(R.layout.item_order_collect, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderData item) {

        helper.setText(R.id.textview_bcode, item.getCodeProduct());
        helper.setText(R.id.textview_location, item.getLocationString());
    }
}


