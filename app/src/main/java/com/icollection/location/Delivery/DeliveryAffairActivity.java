package com.icollection.location.Delivery;

import android.content.Intent;
import android.os.Bundle;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.Delivery.DeliveryAffair.DeliveryActivity;
import com.icollection.location.Delivery.RMA.RMAActivity;
import com.icollection.location.Delivery.Store.StoreDeliveryActivity;
import com.icollection.location.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryAffairActivity extends NetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryaffair);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.image_view_back)
    public void imageViewBack() {
        finish();
    }

    @OnClick(R.id.constraint_cash)
    public void Cash() {
        Intent intent = new Intent(this, CashActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.constraint_deliveryaffair)
    public void Deliveryaffair() {
        Intent intent = new Intent(this, DeliveryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.constraint_wbpc)
    public void wbpc() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "WBPC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_hpic)
    public void hpic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "HPIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_epic)
    public void epic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "EPIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_gbic)
    public void gbic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "GBIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_nlic)
    public void nlic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "NLIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_fgic)
    public void fgic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "FGIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_cbic)
    public void cbic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "CBIC");
        startActivity(intent);
    }

    @OnClick(R.id.constraint_bsic)
    public void bsic() {
        Intent intent = new Intent(this, RMAActivity.class);
        intent.putExtra("ShopName", "BSIC");
        startActivity(intent);
    }
}

