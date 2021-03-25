package com.icollection.location.Order;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Data.Order.OrderData;
import com.icollection.location.Data.Order.RemoteOrder;
import com.icollection.location.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderCollectActivity extends NetActivity {

    @BindView(R.id.text_view_title)
    TextView textTitle;

    private String strOrderNo;//订单号，例如：PDWBPC20210315
    private List<OrderData> listOrderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_collect);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strOrderNo = bundle.getString("OrderNo");

            String shopname = strOrderNo.substring(2,6);
            textTitle.setText("Collect order for " + shopname);

            disposableAddWithoutProgress(
                    RemoteOrder
                            .getInstance()
                            .getOrderData(strOrderNo),
                    orderDatas -> {

                        listOrderData = orderDatas;


                        OrderData orderData = listOrderData.get(0);

                        //取位置
                        String str = orderData.getLocation().toString();
                        Gson gson = new Gson();
                        OrderData.LocationBean locationBean = gson.fromJson(str, new TypeToken<OrderData.LocationBean>() {
                        }.getType());

                        String str2 = locationBean.get_PL_Location();
                        String str3 = locationBean.get_PL_Location();

                    });
        }
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }
}
