package com.icollection.location.Order;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Order.OrderData;
import com.icollection.location.Data.Order.RemoteOrder;
import com.icollection.location.Data.Order.ResultFromSaveOneToDB;
import com.icollection.location.Location.SeriesAdapter;
import com.icollection.location.R;

import java.util.ArrayList;
import java.util.Collections;
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
    @BindView(R.id.recyclerview_series)
    RecyclerView recyclerviewSeries;

    private String strOrderNo;//订单号，例如：PDWBPC20210315
    private List<OrderData> listOrderData;
    private int currentDataIndex; //当前在界面显示数据，在上面数组中的索引

    private OrderCollectAdapter seriesAdapter;

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
                            .getOrderData_test(strOrderNo), //改成测试版本
                    orderDatas -> {

                        listOrderData = orderDatas;

                        OrderData orderData = listOrderData.get(0);
                        currentDataIndex = 0;
                        oneRecordToShow(orderData);

                        seriesAdapter.setNewData(listOrderData);
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

        initAdapter();
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

        textview_location_value.setText(orderData.getLocationString());
    }

    // X 按钮
    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");
        editQty.setText("");

        editBarcode.requestFocus();
        //强制弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editBarcode,InputMethodManager.SHOW_FORCED); //显示键盘,但是这条代码似乎执行无效果，因此可以使用toggleSoftInput来显示键盘。
    }

    // 向下箭头 按钮
    @OnClick(R.id.image_view_down)
    public void image_view_down() {

        editBarcode.setText(textview_current_bcode.getText().toString().trim());

        editQty.requestFocus();
        //强制弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editQty,InputMethodManager.SHOW_FORCED); //显示键盘,但是这条代码似乎执行无效果，因此可以使用toggleSoftInput来显示键盘。
    }

    //检查barcode，与上面一致后，提交
    @OnClick(R.id.btn_check_ok)
    public void btn_check_ok() {
        if(!checkNotEmpty()){
            return;
        }
        String bcode = editBarcode.getText().toString().trim();
        if(!bcode.equals(textview_current_bcode.getText().toString())){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Error")
                    .content("The barcode entered does not match the current barcode!")
                    .positiveText("OK")
                    .show();
            return;
        }

        disposableAddWithProgress(
                RemoteOrder
                        .getInstance()
                        .saveOneToDB("PDHPPC20160530", bcode, Integer.parseInt(editQty.getText().toString())),
                        //.saveOneToDB(strOrderNo, bcode, Integer.parseInt(editQty.getText().toString())),
                resultFromSaveOneToDBs -> {

                    ResultFromSaveOneToDB result = resultFromSaveOneToDBs.get(0);

                    if(result.getMessage().equals("Scan Successful")) {
                        new MaterialDialog.Builder(OrderCollectActivity.this)
                                .title("Done")
                                .content("Saved successfully to the database!")
                                .positiveText("OK")
                                .show();

                        textview_previous_bcode.setText(editBarcode.getText().toString().trim());
                        textview_previous_num.setText(editQty.getText().toString().trim());

                        listOrderData.remove(0);

                        //List<OrderData> listOrderData2 = seriesAdapter.getData();
                        //listOrderData2.remove(0);
                        seriesAdapter.notifyDataSetChanged();

                        if(!listOrderData.isEmpty()){
                            oneRecordToShow(listOrderData.get(0));
                        } else {
                            new MaterialDialog.Builder(OrderCollectActivity.this)
                                    .title("Finished")
                                    .content("This is the last one!")
                                    .positiveText("OK")
                                    .show();
                        }
//                        currentDataIndex++;
//                        if (currentDataIndex < listOrderData.size()) {
//                            OrderData orderData = listOrderData.get(currentDataIndex);
//                            oneRecordToShow(orderData);
//                        }
                        // TODO listOrderData 留一个所有没有捡到商品的数组？

                        editQty.setText("");
                        editBarcode.setText("");
                        editBarcode.requestFocus();
                    }

                    if(result.getMessage().equals("This Barcode not exist")) {
                        new MaterialDialog.Builder(OrderCollectActivity.this)
                                .title("Error")
                                .content("This Barcode not exist in this order!")
                                .positiveText("OK")
                                .show();
                    }
                });


    }

    //不检查barcode，直接提交
    @OnClick(R.id.btn_ok)
    public void btn_ok() {
        if(!checkNotEmpty()){
            return;
        }

        int d = 2;
        // TODO 发送网络请求，成功后更新界面
        // TODO 查找此barcode是否在订单内？
        current_bcode_to_top();
    }

    private void current_bcode_to_top(){
        textview_previous_bcode.setText(editBarcode.getText().toString().trim());
        textview_previous_num.setText(editQty.getText().toString().trim());
    }

    private boolean checkNotEmpty(){

        if(editBarcode.getText().toString().trim().isEmpty()){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Error")
                    .content("Please input barcode!")
                    .positiveText("OK")
                    .show();
            return false;
        }
        if(editQty.getText().toString().trim().isEmpty()){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Error")
                    .content("Please input quantity!")
                    .positiveText("OK")
                    .show();
            return false;
        }
        if(editQty.getText().toString().trim().equals("0")){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Error")
                    .content("The quantity cannot be 0!")
                    .positiveText("OK")
                    .show();
            return false;
        }
        return true;
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    /**
     * 绑定适配器，显示系列其他产品位置列表
     */
    private void initAdapter() {

        recyclerviewSeries.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSeries.setItemAnimator(new DefaultItemAnimator());

        seriesAdapter = new OrderCollectAdapter(new ArrayList<>(), this);

        recyclerviewSeries.setAdapter(seriesAdapter);
    }
}
