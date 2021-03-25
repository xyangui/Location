package com.icollection.location.Order;

import android.os.Bundle;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.R;

import butterknife.ButterKnife;

public class OrderCollectActivity extends NetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_collect);
        ButterKnife.bind(this);
    }
}
