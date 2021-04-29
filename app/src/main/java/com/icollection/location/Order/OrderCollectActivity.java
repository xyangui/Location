package com.icollection.location.Order;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.TransInformation;
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
    @BindView(R.id.edit_barcode)
    EditText editBarcode;
    @BindView(R.id.edit_qty)
    EditText editQty;
    @BindView(R.id.textview_previous_bcode)
    TextView textview_previous_bcode;
    @BindView(R.id.textview_previous_num)
    TextView textview_previous_num;
    @BindView(R.id.textview_current_bcode)
    TextView textview_current_bcode;
    @BindView(R.id.textview_description)
    TextView textview_description;
    @BindView(R.id.textview_stock_shop_value)
    TextView textview_stock_shop_value;
    @BindView(R.id.textview_stock_wh_value)
    TextView textview_stock_wh_value;
    @BindView(R.id.textview_max_sold_value)
    TextView textview_max_sold_value;
    @BindView(R.id.textview_shop_order_value)
    TextView textview_shop_order_value;
    @BindView(R.id.textview_result_value)
    TextView textview_result_value;
    @BindView(R.id.textview_remark_value)
    TextView textview_remark_value;
    @BindView(R.id.textview_actual_send_value)
    TextView textview_actual_send_value;
    @BindView(R.id.textview_location_value)
    TextView textview_location_value;

    private String strOrderNo;//订单号，例如：PDWBPC20210315
    private List<OrderData> listOrderData;
    private int currentDataIndex; //当前在界面显示数据，在上面数组中的索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_collect);
        ButterKnife.bind(this);

        editBarcode.setTransformationMethod(new TransInformation());//全部转换成大写

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
                        currentDataIndex = 0;
                        oneRecordToShow(orderData);
                    });
        }

        editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {
                    //手持机扫描出barcode后
                    String barcode_from_scanner = editBarcode.getText().toString().trim();
                    String current_bcode = textview_current_bcode.getText().toString().trim();

                    if(barcode_from_scanner.equals(current_bcode)){
                        editQty.requestFocus();
                    } else {
                        new MaterialDialog.Builder(OrderCollectActivity.this)
                                .title("Error")
                                .content("You collect wrong product, please change to the correct one.")
                                .positiveText("OK")
                                .show();
                    }
                }
                return false;
            }
        });
    }

    private void oneRecordToShow(OrderData orderData) {

        textview_current_bcode.setText(orderData.getCodeProduct());
        textview_description.setText(orderData.getTitle());
        textview_stock_shop_value.setText(orderData.getStock_shop());
        textview_stock_wh_value.setText(orderData.getStock_wh());
        textview_max_sold_value.setText(orderData.getMax_sold());
        textview_shop_order_value.setText(orderData.getShop_actual_order());
        textview_result_value.setText(orderData.getRec_qty());
        textview_actual_send_value.setText(orderData.getActual_send());
        textview_remark_value.setText(orderData.getRemark());

        //取位置   {PL=[Null], EB=[Null]}  String str = "{PL=[Null], EB=[Null]}";
        String str = orderData.getLocation().toString();
        Gson gson = new Gson();
        OrderData.LocationBean locationBean = gson.fromJson(str, new TypeToken<OrderData.LocationBean>() {
        }.getType());

        String location = locationBean.get_PL_Location();
        textview_location_value.setText(location);
    }

    // X 按钮
    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");
        editQty.setText("");

        editBarcode.requestFocus();
    }

    // 向下箭头 按钮
    @OnClick(R.id.image_view_down)
    public void image_view_down() {

        editBarcode.setText(textview_current_bcode.getText().toString().trim());

        editQty.requestFocus();
    }

    @OnClick(R.id.btn_submit)
    public void btn_submit() {

        currentDataIndex++;
        if(currentDataIndex < listOrderData.size()) {
            OrderData orderData = listOrderData.get(currentDataIndex);
            oneRecordToShow(orderData);
        }

        //发送网络请求，成功后更新界面
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }
}
