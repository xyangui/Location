package com.icollection.location.Delivery.RMA;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Data.Delivery.PartsRMA;
import com.icollection.location.Data.Delivery.RemoteDelivery;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RMAActivity extends NetActivity {

    @BindView(R.id.text_view_title)
    TextView textTitle;
    @BindView(R.id.edit_barcode)
    EditText editBarcode;
    @BindView(R.id.recyclerview_series)
    RecyclerView recyclerviewSeries;
    @BindView(R.id.edit_stuff_password)
    EditText editStuffPassword;
    @BindView(R.id.edit_driver_password)
    EditText editDriverPassword;

    private PartsRMAAdapter partsRMAAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//避免软键盘覆盖屏幕
        setContentView(R.layout.activity_rma);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String shopName = bundle.getString("ShopName");
            textTitle.setText(shopName);
        }

        editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {
                    //手持机扫描出barcode后
                    String num = editBarcode.getText().toString();
                    PartsRMA partsRMA = new PartsRMA();
                    partsRMA.setParts_RMA_num(num);

                    partsRMAAdapter.addData(partsRMA);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editBarcode.requestFocus();
                            editBarcode.setText("");
                        }
                    }, 1500);    //延时1.5s执行
                }
                return false;
            }
        });

        initAdapter();
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    // OK 按钮
    @OnClick(R.id.btn_ok)
    public void btnOK() {

        String num = editBarcode.getText().toString();
        PartsRMA partsRMA = new PartsRMA();
        partsRMA.setParts_RMA_num(num);

        partsRMAAdapter.addData(partsRMA);

        //editBarcode.setFocusable(true);
        //editBarcode.setFocusableInTouchMode(true);
        editBarcode.requestFocus();
        editBarcode.setText("");
    }

    // NO 按钮
    @OnClick(R.id.btn_no)
    public void btnNO() {
        partsRMAAdapter.setNewData(null);
    }

    // CONFIRM 按钮
    @OnClick(R.id.btn_confirm)
    public void btnConfirm() {

        String RMAList = "";
        List<PartsRMA> partsRMAs = partsRMAAdapter.getData();
        for(PartsRMA partsRMA : partsRMAs) {
            if(RMAList.isEmpty()){
                RMAList = partsRMA.getParts_RMA_num();
            } else {
                RMAList = RMAList + "-" + partsRMA.getParts_RMA_num();
            }
        }

        String passwordStuff = editStuffPassword.getText().toString();
        String passwordDriver = editDriverPassword.getText().toString();

        disposableAddWithProgress(
                RemoteDelivery
                        .getInstance()
                        .sendPartsRMAList(RMAList, passwordStuff, passwordDriver),
                partsRMAResult -> {

                    if(partsRMAResult.get(0).getResult().equals("error")){
                        new MaterialDialog.Builder(this)
                                .title("Error")
                                .content(partsRMAResult.get(0).getMessage())
                                .positiveText("OK")
                                .show();
                    }
                });
    }

    /**
     * 绑定适配器，显示系列其他产品位置列表
     */
    private void initAdapter() {

        recyclerviewSeries.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerviewSeries.setItemAnimator(new DefaultItemAnimator());

        partsRMAAdapter = new PartsRMAAdapter(new ArrayList<>(), this);

        recyclerviewSeries.setAdapter(partsRMAAdapter);
    }
}
