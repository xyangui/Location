package com.icollection.location.Delivery.Store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Data.Cash.Cash;
import com.icollection.location.Data.Cash.LocalCash;
import com.icollection.location.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreDeliveryActivity extends NetActivity {

    @BindView(R.id.recyclerview_delivery)
    RecyclerView recyclerviewDelivery;

    private StoreDeliveryAdapter storeDeliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);
        ButterKnife.bind(this);

        initAdapter();
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    private void initAdapter() {

        recyclerviewDelivery.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewDelivery.setItemAnimator(new DefaultItemAnimator());

        storeDeliveryAdapter = new StoreDeliveryAdapter(new ArrayList<>(), this);

        recyclerviewDelivery.setAdapter(storeDeliveryAdapter);
        recyclerviewDelivery.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter,
                                          final View view, final int position) {

                itemClick(adapter, view, position);
            }
        });

        setNewDataTocashAdapter();
    }

    private void setNewDataTocashAdapter() {

        databaseWithoutProgress(
                LocalCash
                        .getInstance()
                        .getCashList(),
                cashList -> {

                    storeDeliveryAdapter.setNewData(cashList);

                });
    }

    @OnClick(R.id.floating_button_add)
    public void Add() {
        Intent intent = new Intent(this, StoreDeliveryAddActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.image_view_add)
    public void imageViewAdd() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_cash, true)
                .positiveText("OK")
                .onPositive((dialog1, which) -> {

                    //saveToRealm(dialog1);

                }).build();

        //new data -> MaterialDialog

        Spinner spin = (Spinner) dialog.getCustomView().findViewById(R.id.spinner);

        List<String> strList3 = new ArrayList<>();
        strList3.add("Petrol");
        strList3.add("Stamp");
        strList3.add("PostBag");
        strList3.add("Other");

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,strList3);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        Cash cash = new Cash();
        TextView textId = dialog.getCustomView().findViewById(R.id.text_id);
        textId.setText(String.valueOf(cash.getId()));

        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(currentDate);

        TextView textTime = dialog.getCustomView().findViewById(R.id.text_time);
        textTime.setText(str);

        dialog.show();
    }

    private void itemClick(final BaseQuickAdapter adapter,
                           final View view, final int position){

    }

}
