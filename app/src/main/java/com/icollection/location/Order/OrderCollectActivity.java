package com.icollection.location.Order;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Order.OrderData;
import com.icollection.location.Data.Order.RemoteOrder;
import com.icollection.location.Data.Order.ResultFromSaveOneToDB;
import com.icollection.location.R;

import java.util.ArrayList;
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
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.text_less_value)
    TextView text_less_value;
    @BindView(R.id.text_g_less_value)
    TextView text_g_less_value;
    @BindView(R.id.text_correct_value)
    TextView text_correct_value;
    @BindView(R.id.text_g_correct_value)
    TextView text_g_correct_value;

    private String strOrderNo;//订单号，例如：PDWBPC20210315
    private List<OrderData> listOrderData;//保存所有"没有捡到"商品列表
    private OrderData previous_orderData;//保存上一个刚刚捡过的商品
    private OrderData current_orderData;//保存当前的商品，不在数组里面

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
            textTitle.setText(shopname);

            //http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/count-kt-warehouse/shop/EPIC
            disposableAddWithoutProgress(
                    RemoteOrder
                            .getInstance()
                            //.getOrderData_test(strOrderNo), //改成测试版本
                            //.getOrderData(strOrderNo),//正式版本
                            .getOrderData("PDEPIC20210531"), //测试版本
                    orderDatas -> {

                        listOrderData = orderDatas;

                        find_data_with_locaction();

                        current_orderData = listOrderData.get(0);
                        listOrderData.remove(0);

                        oneRecordToShow(current_orderData);
                        seriesAdapter.setNewData(listOrderData);

                        previous_orderData = null;
                        btnUpdate.setEnabled(false);
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
                                .content("You collect wrong product, please change to the right one.")
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

        String location0 = current_orderData.getLocationString().substring(0,1);
        if(location0.equals("G") || location0.equals("g")) {

            int value;
            int actual_send = Integer.parseInt(editQty.getText().toString().trim());
            int i = Integer.parseInt(current_orderData.getShop_actual_order());
            if(i == actual_send){

                value = Integer.parseInt(text_g_correct_value.getText().toString()) + 1;
                text_g_correct_value.setText(String.valueOf(value));
            }
            if(i > actual_send){
                value = Integer.parseInt(text_g_less_value.getText().toString()) + 1;
                text_g_less_value.setText(String.valueOf(value));
            }

            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Done")
                    .content("Please double check!")
                    .positiveText("OK")
                    .show();
            return;
        }

        disposableAddWithProgress(
                RemoteOrder
                        .getInstance()
                        //.saveOneToDB("PDHPPC20160530", bcode, Integer.parseInt(editQty.getText().toString())),//测试版本
                        .saveOneToDB("PDEPIC20210531", bcode, Integer.parseInt(editQty.getText().toString())),
                        //.saveOneToDB(strOrderNo, bcode, Integer.parseInt(editQty.getText().toString())),
                resultFromSaveOneToDBs -> {

                    ResultFromSaveOneToDB result = resultFromSaveOneToDBs.get(0);

                    if(result.getMessage().equals("Scan Successful")) {

                        int value;
                        int actual_send = Integer.parseInt(editQty.getText().toString().trim());
                        int i = Integer.parseInt(current_orderData.getShop_actual_order());
                        if(i == actual_send){

                            value = Integer.parseInt(text_correct_value.getText().toString()) + 1;
                            text_correct_value.setText(String.valueOf(value));
                        }
                        if(i > actual_send){
                            value = Integer.parseInt(text_less_value.getText().toString()) + 1;
                            text_less_value.setText(String.valueOf(value));
                        }

                        textview_previous_bcode.setText(editBarcode.getText().toString().trim());
                        textview_previous_num.setText(editQty.getText().toString().trim());
                        previous_orderData = current_orderData;
                        previous_orderData.setActual_send(editQty.getText().toString().trim());
                        btnUpdate.setEnabled(true);

                        boolean is_last_one = false;

                        if(!listOrderData.isEmpty()){

                            if(find_data_with_locaction()){
                                current_orderData = listOrderData.get(0);
                                listOrderData.remove(0);

                                oneRecordToShow(current_orderData);
                                seriesAdapter.notifyDataSetChanged();
                            } else {
                                is_last_one = true;
                            }

                        } else {

                            textview_actual_send_value.setText(editQty.getText().toString().trim());
                            is_last_one = true;
                        }

                        if(is_last_one){
                            new MaterialDialog.Builder(OrderCollectActivity.this)
                                    .title("Finished")
                                    .content("This is the last one!")
                                    .positiveText("OK")
                                    .show();
                        }

                        new MaterialDialog.Builder(OrderCollectActivity.this)
                                .title("Done")
                                .content("Saved successfully to the database!")
                                .positiveText("OK")
                                .show();

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

    //跳过当前商品，把当前商品放到数组最后，显示下一个商品
    @OnClick(R.id.btn_next)
    public void btn_next() {

        if(!find_data_with_locaction()){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Notice")
                    .content("This is the last one with location!")
                    .positiveText("OK")
                    .show();
            return;
        }

        listOrderData.add(current_orderData);

        current_orderData = listOrderData.get(0);
        listOrderData.remove(0);

        oneRecordToShow(current_orderData);
        seriesAdapter.notifyDataSetChanged();
    }

    // REMOVE 按钮
    @OnClick(R.id.btn_remove)
    public void btn_remove() {

        if(!find_data_with_locaction()){
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Notice")
                    .content("This is the last one with location!")
                    .positiveText("OK")
                    .show();
            return;
        }

        current_orderData = listOrderData.get(0);
        listOrderData.remove(0);

        oneRecordToShow(current_orderData);
        seriesAdapter.notifyDataSetChanged();
    }

    /**
     * 找到有位置的数据，并且放置到数组 0 位置
     * @return
     */
    private boolean find_data_with_locaction(){

        String strLocation = listOrderData.get(0).getLocationString();
        if(!strLocation.equals("NONE") && !strLocation.equals("recall")){
           return true;
        }

        int num = 0;
        int size = listOrderData.size();
        while (num < size){
            num++;

            OrderData bean = listOrderData.get(0);
            strLocation = bean.getLocationString();

            listOrderData.remove(0);

            if(!strLocation.equals("NONE") && !strLocation.equals("recall")){

                listOrderData.add(0, bean);
                return true;
            } else {

                listOrderData.add(bean);
            }
        }

        return false;
    }

    // REMOVE ALL 按钮
    @OnClick(R.id.btn_remove_all)
    public void btn_remove_all() {

        int num = 0;
        int size = listOrderData.size();

        String str;

        while (num < size){
            num++;

            OrderData bean = listOrderData.get(0);
            listOrderData.remove(0);

            if(bean.getActual_send() == null){
                listOrderData.add(bean);
            }
        }

        if(find_data_with_locaction()){
            current_orderData = listOrderData.get(0);
            listOrderData.remove(0);

            oneRecordToShow(current_orderData);
            seriesAdapter.notifyDataSetChanged();

            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Done")
                    .content("All collected data was successfully removed!")
                    .positiveText("OK")
                    .show();
        } else {
            new MaterialDialog.Builder(OrderCollectActivity.this)
                    .title("Done")
                    .content("All collected data was successfully removed and complete the collecting!")
                    .positiveText("OK")
                    .show();
        }
    }

    /**
     * true:要么current_orderData本身不是"NONE""recall"，要么已经换成不是"NONE""recall"的数据
     * false:数组中全是"NONE""recall"
     * @return
     */
//    private boolean confirm_current_with_locaction() {
//
//        int size = listOrderData.size();
//        int num = 0;
//        String strLocation = current_orderData.getLocationString();
//        while ((strLocation.equals("NONE") || strLocation.equals("recall")) && (num < size)){
//            num++;
//            listOrderData.add(current_orderData);
//            current_orderData = listOrderData.get(num);
//            listOrderData.remove(0);
//            strLocation = current_orderData.getLocationString();
//        }
//
//        if(num == size){
//            new MaterialDialog.Builder(OrderCollectActivity.this)
//                    .title("Notice")
//                    .content("This is the last one with location!")
//                    .positiveText("OK")
//                    .show();
//            return false;
//        }
//        return true;
//    }

    // UPDATE 按钮
    @OnClick(R.id.btn_update)
    public void btnUpdate() {
        if(previous_orderData == null){
            return;
        }

        listOrderData.add(0, current_orderData);
        seriesAdapter.notifyDataSetChanged();

        current_orderData = previous_orderData;
        oneRecordToShow(current_orderData);

        editBarcode.setText(current_orderData.getCodeProduct());
        editQty.setText(current_orderData.getActual_send());

        editQty.requestFocus();
        //强制弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editQty,InputMethodManager.SHOW_FORCED); //显示键盘,但是这条代码似乎执行无效果，因此可以使用toggleSoftInput来显示键盘。


        btnUpdate.setEnabled(false);
    }

//    private void current_bcode_to_top(){
//        textview_previous_bcode.setText(editBarcode.getText().toString().trim());
//        textview_previous_num.setText(editQty.getText().toString().trim());
//    }

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
