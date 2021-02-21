package com.icollection.location.Base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class IsConnectNet {

    private Context context;

    public IsConnectNet(Context context) {
        this.context = context;
    }

    public boolean isNetOk() {

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State wifiState = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (wifiState.equals(NetworkInfo.State.CONNECTED)) {
            Log.i("TAG", "当前wifi网络连接");
            return true;
        }

        return false;
    }

    //判断网络状态
    public boolean isConnectInternet() {

        ConnectivityManager conManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null) { // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();

        }

        return false;
    }
}
