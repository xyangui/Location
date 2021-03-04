package com.icollection.location.Location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.WindowManager;
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
import com.icollection.location.Data.Location.LocationGetEB;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.R;
import com.icollection.location.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class LocationEBActivity extends NetActivity {

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

    private SeriesAdapterEB seriesAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        //textDescription.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_eb);
        ButterKnife.bind(this);

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //如果是 LOCATION 调用本页
            String str = bundle.getString("barcode");
            editBarcode.setText(str);
            btnEnterJson();

            //禁止软键盘弹出
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        editBarcode.setText("APIPHXRLC103-0");
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    // X 按钮
    @OnClick(R.id.btn_clear)
    public void btnClear() {

        editBarcode.setText("");
        textDescription.setText("");
        editLocation.setText("");

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
            "http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-check-location/barcode2/APIPHXSMHC103-0";
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
//        editBarcode.requestFocus();

        disposableAddWithProgress(
                RemoteLocation
                        .getInstance()
                        .getLocation(barcode),
                strHtml -> {

                    jsonToShow(strHtml);
                });
    }

    private List<LocationGetEB> jsonToShow(String strHtml) {

        if (strHtml.isEmpty() || strHtml.equals("null")) {
            ToastUtil.showShort(this, "No items were found!");
            return null;
        }

        Gson gson = new Gson();
        List<LocationGetEB> locationGets = gson.fromJson(strHtml, new TypeToken<List<LocationGetEB>>() {
        }.getType());

        for (LocationGetEB locationGet : locationGets) {

            String str = locationGet.getBcode().trim();
            String str2 = editBarcode.getText().toString().trim();
            if (str.equals(str2)) {
                String locationEB = locationGet.getLocation_list().get_EB_Location();
                editLocation.setText(locationEB);
                textDescription.setText(locationGet.getDescription());
            }
        }

        seriesAdapter.setNewData(locationGets);

        return locationGets;
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
                        .editLocationEB(barcode, location.toUpperCase()),
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
                        .editAllLocationEB(barcode, location.toUpperCase())
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
                        .addLocationEB(barcode, location.toUpperCase()),
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
                        .addAllLocationEB(barcode, location.toUpperCase()),
                strHtml -> {

                    jsonToShow(strHtml);

                    //List<LocationGetEB> locationGetEBs = jsonToShow(strHtml);

//                    String listBcode = "";
//                    for (LocationGetEB locationGet : locationGetEBs) {
//
//                        int status = locationGet.getStatus();
//                        if (status == 1) { // =0时，添加成功；=1时，添加失败
//
//                            if(listBcode.isEmpty()){
//                                listBcode = locationGet.getBcode();
//                            } else {
//                                listBcode = listBcode + ";" + locationGet.getBcode();
//                            }
//                        }
//                    }
//
//                    if(!listBcode.isEmpty()){
//
////                        new MaterialDialog.Builder(LocationEBActivity.this)
////                                .title("Warning")
////                                .content(listBcode + " Already with location, Use EDIT_ALL!")
////                                .positiveText("OK")
////                                .show();
//                    }
                });
    }

    /**
     * 绑定适配器，显示系列其他产品位置列表
     */
    private void initAdapter() {

        recyclerviewSeries.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewSeries.setItemAnimator(new DefaultItemAnimator());

        seriesAdapter = new SeriesAdapterEB(new ArrayList<>(), this, true);
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
