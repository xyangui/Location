package com.icollection.location.Location;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.icollection.location.Data.Location.LocationGetEB;
import com.icollection.location.R;

import java.util.List;

public class SeriesAdapterEB extends BaseQuickAdapter<LocationGetEB, BaseViewHolder> {

    private Context context;
    //private boolean isEbay;

    public SeriesAdapterEB(List<LocationGetEB> data, Context context) {
        super(R.layout.item_series, data);
        this.context = context;
        //this.isEbay = isEbay;
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationGetEB item) {

        helper.setText(R.id.text_bcode, item.getBcode());
        helper.setText(R.id.text_des, item.getDescription());

        //if(isEbay) {
            helper.setText(R.id.text_pl, item.getLocation_list().get_EB_Location());
        //} else {
            //helper.setText(R.id.text_pl, item.getLocation_list().get_PL_Location());
        //}
    }
}

