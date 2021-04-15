package com.icollection.location.Delivery.RMA;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Delivery.PartsRMA;
import com.icollection.location.R;

import java.util.List;

public class PartsRMAAdapter extends BaseQuickAdapter<PartsRMA, BaseViewHolder> {

    private Context context;

    public PartsRMAAdapter(List<PartsRMA> data, Context context) {
        super(R.layout.item_parts_rma, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PartsRMA item) {

        helper.setText(R.id.text_parts_rma_num, item.getParts_RMA_num());
    }
}
