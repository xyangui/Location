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

import com.icollection.location.Base.NetActivity;
import com.icollection.location.Data.Delivery.PartsRMA;
import com.icollection.location.R;

import java.util.ArrayList;

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
