package com.icollection.location.Order;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Data.Order.RemoteOrder;
import com.icollection.location.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopListActivity extends NetActivity {

    @BindView(R.id.text_view_title)
    TextView textTitle;
    @BindView(R.id.text_view_wbpc_ready)
    TextView WBPC_Ready;
    @BindView(R.id.text_view_hpic_ready)
    TextView HPIC_Ready;
    @BindView(R.id.text_view_epic_ready)
    TextView EPIC_Ready;
    @BindView(R.id.text_view_gbic_ready)
    TextView GBIC_Ready;
    @BindView(R.id.text_view_nlic_ready)
    TextView NLIC_Ready;
    @BindView(R.id.text_view_fgic_ready)
    TextView FGIC_Ready;
    @BindView(R.id.text_view_cbic_ready)
    TextView CBIC_Ready;
    @BindView(R.id.text_view_bsic_ready)
    TextView BSIC_Ready;


    private String order_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTimeInMillis(System.currentTimeMillis());

            String str = bundle.getString("MondayOrFriday");
            if(str.equals("Monday")) {

                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                order_date = simpleDateFormat.format(calendar.getTime());
                textTitle.setText("Weekly Order List");

            } else {

                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                order_date = simpleDateFormat.format(calendar.getTime());
                textTitle.setText("Extra Order List");
            }

            askForRemote("WBPC", "PDWBPC20210315", WBPC_Ready);
            askForRemote("HPIC", "PDHPIC20210319", HPIC_Ready);
            askForRemote("EPIC", "PDEPIC20210322", EPIC_Ready);
            askForRemote("GBIC", "PDGBIC20210315", GBIC_Ready);
            askForRemote("NLIC", "PDNLIC20210319", NLIC_Ready);
            askForRemote("FGIC", "PDFGIC20210322", FGIC_Ready);
            askForRemote("CBIC", "PDCBIC20210319", CBIC_Ready);
            askForRemote("BSIC", "PDBSIC20210322", BSIC_Ready);
        }
    }

    private void askForRemote(String shopname, String orderno, TextView textView) {
        disposableAddWithoutProgress(
                RemoteOrder
                        .getInstance()
                        .getOrderData(shopname, orderno),
                strHtml -> {

                    if (strHtml.isEmpty() || strHtml.equals("null")) {
                        textView.setVisibility(View.INVISIBLE);
                        return;
                    }

                    textView.setVisibility(View.VISIBLE);

                    //String str2 = strHtml;
                    //String str3 = strHtml;
                    //jsonToShow(strHtml);
                });
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }


}
