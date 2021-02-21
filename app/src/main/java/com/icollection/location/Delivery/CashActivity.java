package com.icollection.location.Delivery;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Data.Cash.Cash;
import com.icollection.location.Data.Cash.LocalCash;
import com.icollection.location.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashActivity extends NetActivity {

    @BindView(R.id.recyclerview_cash)
    RecyclerView recyclerviewCash;

    private CashAdapter cashAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);

        initAdapter();
    }

    private void initAdapter() {

        recyclerviewCash.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewCash.setItemAnimator(new DefaultItemAnimator());

        cashAdapter = new CashAdapter(new ArrayList<>(), this);

        recyclerviewCash.setAdapter(cashAdapter);
        recyclerviewCash.addOnItemTouchListener(new OnItemClickListener() {
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

                    cashAdapter.setNewData(cashList);

                });
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    @OnClick(R.id.floating_button_add)
    public void Add() {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_cash, true)
                .positiveText("OK")
                .onPositive((dialog1, which) -> {

                    saveToRealm(dialog1);

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

    @OnClick(R.id.image_view_sum)
    public void imageViewSum() {
        databaseWithoutProgress(
                LocalCash
                        .getInstance()
                        .getCashList(),
                cashList -> {

                    float fSum = 0.0f;
                    for (Cash cash: cashList){
                        if(cash.getIsToDriver() && cash.getIsToAccountant()){
                            fSum += cash.getSum();
                        }
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String str = decimalFormat.format(fSum);

                    new MaterialDialog.Builder(this)
                            .title("Sum")
                            .content("The sum of all already to accountant and driver is " + str)
                            .positiveText("OK")
                            .show();

                });
    }

    private void saveToRealm(MaterialDialog dialog1) {

        Spinner spinner = dialog1.getCustomView().findViewById(R.id.spinner);

        TextView textId = dialog1.getCustomView().findViewById(R.id.text_id);
        EditText editSum = dialog1.getCustomView().findViewById(R.id.edit_sum);
        TextView textTime = dialog1.getCustomView().findViewById(R.id.text_time);
        CheckBox checkAccountant = dialog1.getCustomView().findViewById(R.id.check_accountant);
        CheckBox checkMe = dialog1.getCustomView().findViewById(R.id.check_me);

        Cash object1 = new Cash();
        object1.setId(Integer.valueOf(textId.getText().toString()));
        object1.setType(spinner.getSelectedItem().toString());

        if (editSum.getText().toString().isEmpty()) {
            return;
        }
        object1.setSum(Float.valueOf(editSum.getText().toString()));
        object1.setTime(textTime.getText().toString());
        if (checkAccountant.isChecked()) {
            object1.setIsToAccountant(true);
        } else {
            object1.setIsToAccountant(false);
        }
        if (checkMe.isChecked()) {
            object1.setIsToDriver(true);
        } else {
            object1.setIsToDriver(false);
        }

        LocalCash localCash = LocalCash.getInstance();
        localCash.saveCash(object1);

        CashActivity.this.setNewDataTocashAdapter();
    }

    private void itemClick(final BaseQuickAdapter adapter,
                             final View view, final int position) {

        MaterialDialog dialog = new MaterialDialog.Builder(CashActivity.this)
                .customView(R.layout.dialog_cash, true)
                .positiveText("OK")
                .onPositive((dialog1, which) -> {

                    saveToRealm(dialog1);

                })
                .negativeText("DELETE")
                .onNegative((dialog2, which) -> {

                    TextView textId = dialog2.getCustomView().findViewById(R.id.text_id);

                    LocalCash localCash = LocalCash.getInstance();
                    localCash.delete(Integer.valueOf(textId.getText().toString()));

                    CashActivity.this.setNewDataTocashAdapter();
                }).build();

        //data -> MaterialDialog
        Cash cash = (Cash) adapter.getData().get(position);

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

        int num = 0, currentNum = 0;
        for (String string: strList3){
            if(cash.getType().equals(string)){
                num = currentNum;
                break;
            }
            currentNum++;
        }
        spin.setSelection(num);


        TextView textId = dialog.getCustomView().findViewById(R.id.text_id);
        textId.setText(String.valueOf(cash.getId()));

        EditText editSum = dialog.getCustomView().findViewById(R.id.edit_sum);
        editSum.setText(String.valueOf(cash.getSum()));
        TextView textTime = dialog.getCustomView().findViewById(R.id.text_time);
        textTime.setText(cash.getTime());

        CheckBox checkAccountant = dialog.getCustomView().findViewById(R.id.check_accountant);
        if (cash.getIsToAccountant()) {
            checkAccountant.setChecked(true);
        } else {
            checkAccountant.setChecked(false);
        }
        CheckBox checkMe = dialog.getCustomView().findViewById(R.id.check_me);
        if (cash.getIsToDriver()) {
            checkMe.setChecked(true);
        } else {
            checkMe.setChecked(false);
        }

        dialog.show();
    }

}

