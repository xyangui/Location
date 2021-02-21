package com.icollection.location.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class NetActivity extends AppCompatActivity
        implements NetProgress, Thread.UncaughtExceptionHandler { //捕获异常 1

    private IsConnectNet isConnectNet;
    private CompositeDisposable compositeDisposable;
    private NetRequest netRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //App.getInstance().addActivity(this);

        isConnectNet = new IsConnectNet(this);
        compositeDisposable = new CompositeDisposable();
        netRequest = new NetRequest(this,this);

        //捕获异常 2
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        //捕获异常 3
        new MaterialDialog.Builder(this)
                .content("crash")
                .positiveText("是")
                .negativeText("否")
                .show();
    }

    /**
     * 显示 BaseProgress 时，不调用 onPause，上面再显示 Activity 时调用
     */
    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
    /**
     * 取消ProgressDialog的时候，取消对observable的订阅
     */
    @Override
    public void onCancelProgress() {
        compositeDisposable.clear();
    }

    /**
     * 访问数据库时"不带"加载ProgressBar
     * @param o
     * @param onNext
     * @param <T>
     */
    protected <T> void databaseWithoutProgress(Flowable<T> o,
                                                    Consumer<T> onNext) {

//        if (!isConnectInternet()){
//            ToastUtil.showLong(this, "无网络");
//            return;
//        }

        //compositeDisposable.add(bindObservableConsumerWithoutProgress(o, onNext));
        compositeDisposable.add(
                netRequest.bindObservableConsumerWithoutProgress(o, onNext));
    }

    /**
     * 网络请求时"不带"加载ProgressBar
     * @param o
     * @param onNext
     * @param <T>
     */
    protected <T> void disposableAddWithoutProgress(Flowable<T> o,
                                                    Consumer<T> onNext) {

        if (!isConnectInternet()){
            ToastUtil.showLong(this, "无网络");
            return;
        }

        //compositeDisposable.add(bindObservableConsumerWithoutProgress(o, onNext));
        compositeDisposable.add(
                netRequest.bindObservableConsumerWithoutProgress(o, onNext));
    }
    /**
     * 网络请求时"带"加载"ProgressBar"
     * @param o
     * @param onNext
     * @param <T>
     */
    protected <T> void disposableAddWithProgress(Flowable<T> o,
                                                 Consumer<T> onNext) {

        if (!isConnectInternet()){
            ToastUtil.showLong(this, "无网络");
            return;
        }

        //compositeDisposable.add(bindObservableConsumerWithProgress(activity, o, onNext));
        compositeDisposable.add(
                netRequest.bindObservableConsumerWithProgress(o, onNext));
    }

    public void getNetState() {
        if (isConnectNet.isNetOk() == false && isConnectNet.isConnectInternet() == false) {
            ToastUtil.showShort(this, "网络连接失败，请稍后重试");
        }
    }
    protected boolean isConnectInternet(){
        return isConnectNet.isConnectInternet();
    }

    public void getResultCode(int code, String result) {

        if (code == 1001 || code == -5) {

//            SetOrGetSystemIniXml systemIniXml = new SetOrGetSystemIniXml(this);
//            ToastUtil.showShort(this, "您的身份验证已过期，请重新登录");
//
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.putExtra("role", systemIniXml.GetKeyValueString("UserRole"));
//            startActivity(intent);
//            finish();
//
//            SettingActivity.instance.finish();
//            MainActivity.instance.finish();

        } else {
            ToastUtil.showShort(this, result);
        }
    }

    protected void snackbarSubmitOK(View view) {

        //Snackbar.make(view, "提交成功！", Snackbar.LENGTH_SHORT)
        //        .setAction("Action", null).show();
    }

    protected void snackbarSubmitFail(View view) {

        //Snackbar.make(view, "提交失败！", Snackbar.LENGTH_SHORT)
        //        .setAction("Action", null).show();
    }

    //toolbar统一处理，设置返回箭头
    protected void initToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            //设置是否有返回箭头
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> {
                finish();
            });
        }
    }
}

