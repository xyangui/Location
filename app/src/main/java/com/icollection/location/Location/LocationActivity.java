package com.icollection.location.Location;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Base.TransInformation;
import com.icollection.location.Data.Location.LocationGet;
import com.icollection.location.Data.Location.LocationGet2;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Delivery.DeliveryAffairActivity;
import com.icollection.location.R;
import com.icollection.location.ScanActivity;
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
    @BindView(R.id.edit_eb_location)
    EditText editEBLocation;
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


    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    private SeriesAdapter seriesAdapter;

    //private boolean isAdd; //是否可以add，默认不能点击add，只有No location时，才可以add

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

        BGAQRCodeUtil.setDebug(true);

        editBarcode.setTransformationMethod(new TransInformation());//全部转换成大写
        editLocation.setTransformationMethod(new TransInformation());//全部转换成大写
        //editEBLocation.setTransformationMethod(new TransInformation());//全部转换成大写

        editBarcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {
                    btnEnterJson();
                }
                return false;
            }
        });

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


        initAdapter();

        //isAdd = false;
        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    @OnClick(R.id.btn_to_eb)
    public void btnToEBay() {
        Intent intent = new Intent(this, LocationEBActivity.class);
        intent.putExtra("barcode", editBarcode.getText().toString().toUpperCase());
        startActivity(intent);
    }

    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");

        //editBarcode.setText("APIPHXSMHC103-0");

        textDescription.setText("");
        editLocation.setText("");
        seriesAdapter.setNewData(null);

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        textStockNum.setText("Num");
        editEBLocation.setText("");

        editBarcode.requestFocus();
    }

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

    @OnClick(R.id.btn_enter)
    public void btnEnter() {

//        textDescription.setText("");
//        editLocation.setText("");
//        seriesAdapter.setNewData(null);

        //String barcode = "APIPHXSMHC103-0";
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            ToastUtil.showShort(this, "Please enter barcode!");
            return;
        }
        editBarcode.setText(barcode.toUpperCase());

//        btnAdd.setEnabled(false);
//        btnAddAll.setEnabled(false);

        editBarcode.requestFocus();

        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("web_url", WEB_ADDRESS + editBarcode.getText().toString().toUpperCase());
        startActivity(intent);
    }


//    @OnClick(R.id.btn_scan)
//    public void btnScan() {
//
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra("is_web", "yes_web");
//        startActivity(intent);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 888 && requestCode == 999) {
            String barcode = data.getStringExtra("Barcode");
            editBarcode.setText(barcode);

            textDescription.setText("");
            editLocation.setText("");
            seriesAdapter.setNewData(null);

            //editBarcode.setFocusable(false);

            askWebForLocation(barcode);
        }
    }

    //返回json串，loction地址
    private static final String JSON_ADDRESS =
            "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-check-location/barcode2/APIPHXSMHC103-0";

    //ok 按钮
    @OnClick(R.id.btn_enter_json)
    public void btnEnterJson() {

        textDescription.setText("");
        editLocation.setText("");
        seriesAdapter.setNewData(null);

        //String barcode = "APIPHXSMHC103-0";
        String barcode = editBarcode.getText().toString();
        if (barcode.isEmpty()) {
            ToastUtil.showShort(this, "Please enter barcode!");
            return;
        }
        editBarcode.setText(barcode.toUpperCase());

        askWebForLocation(barcode.toUpperCase());
    }

    private void askWebForLocation(String barcode) {

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

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
            String str2 = editBarcode.getText().toString();
            if (str.equals(str2)) {

                editLocation.setText(locationGet.getLocation_list().get_PL_Location());
                textDescription.setText(locationGet.getDescription());

                textStockNum.setText(locationGet.getStock());
                editEBLocation.setText(locationGet.getLocation_list().get_EB_Location());
            }
        }

        seriesAdapter.setNewData(locationGets);

    }

    //此按钮已经gone
    @OnClick(R.id.btn_scan_json)
    public void btnScanJson() {

        Intent intent = new Intent(this, ScanActivity.class);
        startActivityForResult(intent, 999);
    }

    private static final String EDIT_ADD_ADDRESS =
            "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXSMHC103-0/location/U23/act/edit";

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
        }, 4000);    //延时4s执行

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
        }, 4000);    //延时4s执行

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

        btnAdd.setEnabled(false);
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

        btnAdd.setEnabled(false);
        btnAddAll.setEnabled(false);

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .addAllLocation(barcode, location.toUpperCase()),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    //显示系列其他产品位置列表

    /**
     * 绑定适配器：动态，下拉随着整个 SwipeRefreshLayout 一起刷新，上拉加载
     */
    private void initAdapter() {

        recyclerviewSeries.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSeries.setItemAnimator(new DefaultItemAnimator());

        seriesAdapter = new SeriesAdapter(new ArrayList<>(), this,false);
        //seriesAdapter.setOnLoadMoreListener(this, recyclerviewInterest);
        //seriesAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //seriesAdapter.setLoadMoreView(new CustomLoadMoreView());

        //处理"动态"每一项中的"点赞"，"关注"，"私信"事件
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
