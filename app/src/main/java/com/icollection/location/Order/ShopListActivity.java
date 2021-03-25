package com.icollection.location.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.Base.ToastUtil;
import com.icollection.location.Data.Location.RemoteLocation;
import com.icollection.location.Data.Order.OrderNoRepository;
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


    private String order_date;//星期一或者星期五的日期，例如：20210315

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
                order_date = simpleDateFormat.format(calendar.getTime());//星期一的日期，例如：20210315
                textTitle.setText("Weekly Order List");

            } else {

                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                order_date = simpleDateFormat.format(calendar.getTime());//星期五的日期，例如：20210319
                textTitle.setText("Extra Order List");
            }

            askForRepository("PDWBPC" + order_date, WBPC_Ready);
            askForRepository("PDHPIC" + order_date, HPIC_Ready);
            askForRepository("PDEPIC" + order_date, EPIC_Ready);
            askForRepository("PDGBIC" + order_date, GBIC_Ready);
            askForRepository("PDNLIC" + order_date, NLIC_Ready);
            askForRepository("PDFGIC" + order_date, FGIC_Ready);
            askForRepository("PDCBIC" + order_date, CBIC_Ready);
            askForRepository("PDBSIC" + order_date, BSIC_Ready);
        }
    }

    private void askForRepository(String orderno, TextView textView) {
        disposableAddWithoutProgress(
                OrderNoRepository
                        .getInstance()
                        .getOrderNo(orderno),
                isReady -> {

                    if (isReady) {
                        textView.setVisibility(View.VISIBLE);
                        return;
                    }

                    textView.setVisibility(View.INVISIBLE);
                });
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }


    @OnClick(R.id.constraint_wbpc)
    public void WBPC() {
        if(WBPC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDWBPC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_hpic)
    public void HPIC() {
        if(HPIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDHPIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_epic)
    public void EPIC() {
        if(EPIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDEPIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_gbic)
    public void GBIC() {
        if(GBIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDGBIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_nlic)
    public void NLIC() {
        if(NLIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDNLIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_fgic)
    public void FGIC() {
        if(FGIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDFGIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_cbic)
    public void CBIC() {
        if(CBIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDCBIC" + order_date);
            startActivity(intent);
        }
    }

    @OnClick(R.id.constraint_bsic)
    public void BSIC() {
        if(BSIC_Ready.getVisibility() == View.VISIBLE) {
            Intent intent = new Intent(this, OrderCollectActivity.class);
            intent.putExtra("OrderNo", "PDBSIC" + order_date);
            startActivity(intent);
        }
    }

}
