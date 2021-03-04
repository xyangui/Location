package com.icollection.location.ShopStock;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Data.Location.ShopStockBean;
import com.icollection.location.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopStockActivity extends NetActivity {

    @BindView(R.id.edit_barcode)
    EditText editBarcode;
    @BindView(R.id.textview_description)
    TextView textDescription;
    @BindView(R.id.recyclerview_shop_stock)
    RecyclerView recyclerviewShopStock;

    private ShopStockAdapter shopStockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_stock);
        ButterKnife.bind(this);

        editBarcode.setTransformationMethod(new TransInformation());//全部转换成大写

        editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {
                    //手持机扫描出barcode后
                    btnEnter();
                }
                return false;
            }
        });

        initAdapter();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //如果是 LOCATION 调用本页
            String str = bundle.getString("barcode");
            editBarcode.setText(str);
            textDescription.setText(bundle.getString("description"));
            btnEnter();

            //禁止软键盘弹出
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    /**
     * 绑定适配器，显示列表
     */
    private void initAdapter() {

        recyclerviewShopStock.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewShopStock.setItemAnimator(new DefaultItemAnimator());

        shopStockAdapter = new ShopStockAdapter(new ArrayList<>(), this);

        recyclerviewShopStock.setAdapter(shopStockAdapter);
    }

    // X 按钮
    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");
        textDescription.setText("");

        shopStockAdapter.setNewData(null);

        editBarcode.requestFocus();
    }

    // OK 按钮
    @OnClick(R.id.btn_enter)
    public void btnEnter() {

        shopStockAdapter.setNewData(null);

        String barcode = editBarcode.getText().toString().trim();
        if (barcode.isEmpty()) {
            ToastUtil.showShort(this, "Please enter barcode!");
            return;
        }
        editBarcode.setText(barcode.toUpperCase());
//        editBarcode.requestFocus();

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .getShopStock(barcode),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    private void jsonToShow(String strHtml) {

        if (strHtml.isEmpty() || strHtml.equals("null")) {
            ToastUtil.showShort(this, "No items were found!");
            return;
        }

        Gson gson = new Gson();
        List<ShopStockBean> shopStockBeans = gson.fromJson(strHtml, new TypeToken<List<ShopStockBean>>() {
        }.getType());

//        for (LocationGetEB locationGet : shopStockBeans) {
//
//            String str = locationGet.getBcode().trim();
//            String str2 = editBarcode.getText().toString().trim();
//            if (str.equals(str2)) {
//                String locationEB = locationGet.getLocation_list().get_EB_Location();
//                editLocation.setText(locationEB);
//                textDescription.setText(locationGet.getDescription());
//            }
//        }

        shopStockAdapter.setNewData(shopStockBeans);
    }




}
