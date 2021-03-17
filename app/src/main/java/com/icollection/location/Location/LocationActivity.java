package com.icollection.location.Location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Location.LocationGet;
import com.icollection.location.Data.Location.LocationGetEB;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.R;
import com.icollection.location.ShopStock.ShopStockActivity;
import com.icollection.location.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;

public class LocationActivity extends NetActivity {

    @BindView(R.id.edit_barcode)
    EditText editBarcode;
    @BindView(R.id.textview_description)
    TextView textDescription;
    @BindView(R.id.edit_location)
    EditText editLocation;
    @BindView(R.id.recyclerview_series)
    RecyclerView recyclerviewSeries;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_edit_all)
    Button btnEditAll;
    @BindView(R.id.btn_add_all)
    Button btnAddAll;
    @BindView(R.id.textview_stock_num)
    TextView textStockNum;

    private SeriesAdapter seriesAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        editBarcode.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        //BGAQRCodeUtil.setDebug(true);

        editBarcode.setTransformationMethod(new TransInformation());//全部转换成大写
        editLocation.setTransformationMethod(new TransInformation());//全部转换成大写

        editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {
                    //手持机扫描出barcode后
                    btnEnterJson();
                }
                return false;
            }
        });

        initAdapter();

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

//        editBarcode.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                //s:变化后的所有字符
//                //Toast.makeText(getApplicationContext(), "变化:"+s, Toast.LENGTH_SHORT).show();
//
//                int dd = 3;
//                int dd2 = 3;
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//                //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
//                //Toast.makeText(getApplicationContext(), "变化前:"+s+";"+start+";"+count+";"+after, Toast.LENGTH_SHORT).show();
//                int dd = 3;
//                int dd2 = 3;
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // TODO Auto-generated method stub
//                //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
//                //     Toast.makeText(getApplicationContext(), "变化后:"+s+";"+start+";"+before+";"+count, Toast.LENGTH_SHORT).show();
//                int dd = 3;
//                int dd2 = 3;
//            }
//        });
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    // EBay Location 按钮
    @OnClick(R.id.btn_to_eb)
    public void btnToEBay() {
        Intent intent = new Intent(this, LocationEBActivity.class);
        intent.putExtra("barcode", editBarcode.getText().toString().toUpperCase());
        startActivity(intent);
    }

    // Shop Stock 按钮
    @OnClick(R.id.btn_shop_stock)
    public void btnShopStock() {
        Intent intent = new Intent(this, ShopStockActivity.class);
        intent.putExtra("barcode", editBarcode.getText().toString().toUpperCase());
        intent.putExtra("description", textDescription.getText().toString());
        startActivity(intent);
    }

    // X 按钮
    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");
        textDescription.setText("");
        editLocation.setText("");
        textStockNum.setText("Num");

        seriesAdapter.setNewData(null);

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        editBarcode.requestFocus();
    }

    /**
     * 只有位置为空时，才能添加位置
     */
    @OnFocusChange(R.id.edit_location)
    public void onFocusChanged(boolean focused) {

        String str = editLocation.getText().toString();
        if (focused && str.equals("NONE")) {
            editLocation.setText("");

            btnAdd.setEnabled(true);
            btnAddAll.setEnabled(true);
        }
    }

    //网页访问loction地址
    public static final String WEB_ADDRESS
            = "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/edit-product-location/barcode/";

    // WEB 按钮
    @OnClick(R.id.btn_enter)
    public void btnEnter() {

        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            ToastUtil.showShort(this, "Please enter barcode!");
            return;
        }

        editBarcode.setText(barcode.toUpperCase());
        editBarcode.requestFocus();

        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("web_url", WEB_ADDRESS + editBarcode.getText().toString().toUpperCase());
        startActivity(intent);
    }

    //返回json串，loction地址
    private static final String JSON_ADDRESS =
            "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-check-location/barcode2/APIPHXRLC103-0";
    private static final String EDIT_ADD_ADDRESS =
            "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXSMHC103-0/location/U23/act/edit";

    // OK 按钮
    @OnClick(R.id.btn_enter_json)
    public void btnEnterJson() {

        textDescription.setText("");
        editLocation.setText("");
        seriesAdapter.setNewData(null);

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        String barcode = editBarcode.getText().toString().trim();
        if (barcode.isEmpty()) {
            ToastUtil.showShort(this, "Please enter barcode!");
            return;
        }
        editBarcode.setText(barcode.toUpperCase());
        editBarcode.requestFocus();

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .getLocation(barcode),
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
        List<LocationGet> locationGets = gson.fromJson(strHtml, new TypeToken<List<LocationGet>>() {
        }.getType());

        for (LocationGet locationGet : locationGets) {

            String str = locationGet.getBcode().trim();
            String str2 = editBarcode.getText().toString().trim();
            if (str.equals(str2)) {

                String str33 = locationGet.getLocation_list().get_PL_Location();
                editLocation.setText(locationGet.getLocation_list().get_PL_Location());
                textDescription.setText(locationGet.getDescription());

                textStockNum.setText(locationGet.getStock());
                break;
            }
        }

        seriesAdapter.setNewData(locationGets);
    }

    @OnClick(R.id.btn_edit)
    public void btnEdit() {
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            return;
        }
        String location = editLocation.getText().toString();
        if (location.isEmpty()) {
            return;
        }
        editLocation.setText(location.toUpperCase());


        btnEdit.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnEdit.setEnabled(true);
            }
        }, 4000);    //延时4s执行，防止重复点击
        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .editLocation(barcode, location.toUpperCase()),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    @OnClick(R.id.btn_edit_all)
    public void btnEditAll() {
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            return;
        }
        String location = editLocation.getText().toString();
        if (location.isEmpty()) {
            return;
        }
        editLocation.setText(location.toUpperCase());


        btnEditAll.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnEditAll.setEnabled(true);
            }
        }, 4000);    //延时4s执行，防止重复点击
        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .editAllLocation(barcode, location.toUpperCase())
                        .flatMap(strHtml -> {
                            return RemoteLocation
                                    .getInstance().getLocation(barcode);
                        }),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    @OnClick(R.id.btn_add)
    public void btnAdd() {
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            return;
        }
        String location = editLocation.getText().toString();
        if (location.isEmpty()) {
            return;
        }
        editLocation.setText(location.toUpperCase());

        btnAdd.setEnabled(false);//只能执行一次
        btnAddAll.setEnabled(false);

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .addLocation(barcode, location.toUpperCase()),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    @OnClick(R.id.btn_add_all)
    public void btnAddAll() {
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            return;
        }
        String location = editLocation.getText().toString();
        if (location.isEmpty()) {
            return;
        }
        editLocation.setText(location.toUpperCase());

        btnAdd.setEnabled(false);//只能执行一次
        btnAddAll.setEnabled(false);

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .addAllLocation(barcode, location.toUpperCase()),
                strHtml -> {

                    List<LocationGet> list_in_screen = seriesAdapter.getData();

                    //jsonToShow(strHtml);

                    if (strHtml.isEmpty() || strHtml.equals("null")) {
                        ToastUtil.showShort(this, "No items were found!");
                    }

                    Gson gson = new Gson();
                    List<LocationGet> list_from_json = gson.fromJson(strHtml, new TypeToken<List<LocationGet>>() {
                    }.getType());

                    String listBcode_add_fail = "";

                    for (LocationGet bean_from_json : list_from_json) {

                        String Bcode_from_json = bean_from_json.getBcode().trim();
                        String Bcode_from_edit = editBarcode.getText().toString().trim();
                        if (Bcode_from_json.equals(Bcode_from_edit)) {
                            String location_from_json = bean_from_json.getLocation_list().get_PL_Location();
                            editLocation.setText(location_from_json);
                            textDescription.setText(bean_from_json.getDescription());
                        }

                        //把修改成功的item，改成新的位置
                        if(bean_from_json.getStatus() == 0){ //success

                            for (LocationGet bean_in_screen : list_in_screen) {

                                if(bean_in_screen.getBcode().trim().equals(Bcode_from_json)){

                                    List<String> list = new ArrayList<>();
                                    list.add("PL");
                                    list.add(bean_from_json.getLocation_list().get_PL_Location());

                                    List<List<String>> listlist = new ArrayList<>();
                                    listlist.add(list);

                                    LocationGet.LocationListBean bean = new LocationGet.LocationListBean();
                                    bean.setCode_location(listlist);

                                    bean_in_screen.setLocation_list(bean);//修改bean_in_screen，相当于修改数组内的值
                                }
                            }
                        } else { // =0时，添加成功；=1时，添加失败

                            if(listBcode_add_fail.isEmpty()){
                                listBcode_add_fail = Bcode_from_json;
                            } else {
                                listBcode_add_fail = listBcode_add_fail + ";" + Bcode_from_json;
                            }
                        }
                    }

                    if(!listBcode_add_fail.isEmpty()){

                        new MaterialDialog.Builder(LocationActivity.this)
                                .title("Warning")
                                .content(listBcode_add_fail + " already have location, Use EDIT_ALL!")
                                .positiveText("OK")
                                .show();
                    }

                    seriesAdapter.notifyDataSetChanged();
                });
    }

    /**
     * 绑定适配器，显示系列其他产品位置列表
     */
    private void initAdapter() {

        recyclerviewSeries.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSeries.setItemAnimator(new DefaultItemAnimator());

        seriesAdapter = new SeriesAdapter(new ArrayList<>(), this);
        //seriesAdapter.setOnLoadMoreListener(this, recyclerviewInterest);
        //seriesAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //seriesAdapter.setLoadMoreView(new CustomLoadMoreView());

        //adapterInterest.setOnItemChildClickListener(new ItemChildClickListener(this, mPresenter));

        recyclerviewSeries.setAdapter(seriesAdapter);
//        recyclerviewSeries.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(final BaseQuickAdapter adapter,
//                                          final View view, final int position) {
//
//                // 点击进入动态详情
////                Intent intent = new Intent(getActivity(), PartyDetailActivity.class);
////                intent.putExtra(IntentExtra.PARTY_DETAIL, interestList.get(position));
////                getActivity().startActivity(intent);
//
//            }
//        });
//        recyclerviewSeries.setNestedScrollingEnabled(false);

        //填充数据
//        List<LocationGet> objects = new ArrayList<>();
//
//        LocationGet object1 = new LocationGet();
//        object1.setBcode("APIPHXRLC103-0");
//        object1.setDescription("B0 #B Mercury IPHXR Rich Diary With Card With Card Pocket Black");
//
//        List<String> strList = new ArrayList<>();
//        strList.add("K11");
//        LocationGet.LocationListBean bean = new LocationGet.LocationListBean();
//        bean.setCode_location(strList);
//
//        object1.setLocation_list(bean);
//        objects.add(object1);
//
//        LocationGet object2 = new LocationGet();
//        object2.setBcode("APIPHXRLC103-1");
//        object2.setDescription("B0 #B Mercury IPHXR Rich Diary With Card With Card Pocket Red");
//
//        object2.setLocation_list(bean);
//        objects.add(object2);
//
//        LocationGet object3 = new LocationGet();
//        object3.setBcode("APIPHXRLC103-5");
//        object3.setDescription("B0 #B Mercury IPHXR Rich Diary With Card With Card Pocket Blue");
//
//        List<String> strList3 = new ArrayList<>();
//        strList3.add("K11");
//        strList3.add("K12");
//        LocationGet.LocationListBean bean3 = new LocationGet.LocationListBean();
//        bean3.setCode_location(strList3);
//
//        object3.setLocation_list(bean3);
//        objects.add(object3);

        // seriesAdapter.setNewData(objects);
    }

}

