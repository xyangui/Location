package com.icollection.location.ShopStock;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Location.ShopStockBean;
import com.icollection.location.R;

import java.util.List;

public class ShopStockAdapter extends BaseQuickAdapter<ShopStockBean, BaseViewHolder> {

    private Context context;
    private boolean isEbay;

    public ShopStockAdapter(List<ShopStockBean> data, Context context) {
        super(R.layout.item_series, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopStockBean item) {

        helper.setText(R.id.text_bcode, item.getShop());

        helper.setText(R.id.text_pl, item.getStock());

//        if(isEbay) {
//            helper.setText(R.id.text_pl, item.getLocation_list().get_EB_Location());
//        } else {
//            helper.setText(R.id.text_pl, item.getLocation_list().get_PL_Location());
//        }
    }
}


