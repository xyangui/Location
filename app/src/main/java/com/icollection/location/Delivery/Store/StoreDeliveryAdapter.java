package com.icollection.location.Delivery.Store;

import android.content.Context;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Cash.Cash;
import com.icollection.location.R;

import java.util.List;

public class StoreDeliveryAdapter extends BaseQuickAdapter<Cash, BaseViewHolder> {

    private Context context;

    public StoreDeliveryAdapter(List<Cash> data, Context context) {
        super(R.layout.item_cash, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Cash item) {

        helper.setText(R.id.text_petrol, item.getType());
        helper.setText(R.id.text_sum, String.valueOf(item.getSum()));
        helper.setText(R.id.text_time, item.getTime());

        CheckBox checkAccountant = helper.getView(R.id.check_accountant);
        if(item.getIsToAccountant()){
            checkAccountant.setChecked(true);
        } else {
            checkAccountant.setChecked(false);
        }

        CheckBox checkMe = helper.getView(R.id.check_me);
        if(item.getIsToDriver()){
            checkMe.setChecked(true);
        } else {
            checkMe.setChecked(false);
        }
    }

}
