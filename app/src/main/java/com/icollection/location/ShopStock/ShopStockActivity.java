package com.icollection.location.ShopStock;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Location.LocationGet;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Data.Location.ShopStockBean;
import com.icollection.location.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopStockActivity extends NetActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_stock);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }




}
