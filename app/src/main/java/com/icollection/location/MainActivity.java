package com.icollection.location;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.icollection.location.Base.NetActivity;
import com.icollection.location.Delivery.DeliveryAffairActivity;
import com.icollection.location.Location.LocationActivity;
import com.icollection.location.Location.LocationEBActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import io.realm.Realm;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends NetActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BGAQRCodeUtil.setDebug(true);

        Realm.init(this);

    }

    @OnClick(R.id.constraint_location)
    public void Location() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.constraint_location_eb)
    public void LocationEB() {
        Intent intent = new Intent(this, LocationEBActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.constraint_order)
    public void Order() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.constraint_delivery)
    public void Delivery() {
        Intent intent = new Intent(this, DeliveryAffairActivity.class);
        startActivity(intent);
    }

    //权限申请
    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.VIBRATE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
}
