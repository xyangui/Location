package com.icollection.location.Delivery.DeliveryAffair;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryProcedureActivity extends NetActivity {

    @BindView(R.id.text_date_today)
    TextView textDate;
    @BindView(R.id.text_day_today)
    TextView textDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_procedure);
        ButterKnife.bind(this);

        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(currentDate);
        textDate.setText(str);

        Date date = Calendar.getInstance().getTime();
        textDay.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    @OnClick(R.id.btn_date_set)
    public void btn_date_set() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        textDate.setText(text);

                        String dayOfWeek = DateFormat.format("EEEE",
                                new Date(year, monthOfYear, dayOfMonth - 1)).toString();
                        textDay.setText(dayOfWeek);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

}
