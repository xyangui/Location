package com.icollection.location.Base;

import android.content.Context;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NetRequest {
    private Context context;
    private MaterialDialog materialDialog;
    private NetProgress netProgress;

    public NetRequest(Context context, NetProgress netProgress){
        this.context = context;
        this.netProgress = netProgress;
    }

    public <T> Disposable bindObservableConsumerWithoutProgress(Flowable<T> o,
                                                                Consumer<T> onNext) {

        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        // onError, 异常统一处理
                        e -> {
                            if (e instanceof SocketTimeoutException
                                    || e instanceof ConnectException) {

                                Log.i("TAG", "网络中断，请检查您的网络状态");
                                ToastUtil.showLong(context, "网络中断，请检查您的网络状态");

                            } else {

                                Log.i("TAG", "error:" + e.getMessage());
                                ToastUtil.showLong(context, "error:" + e.getMessage());
                            }
                        },
                        // onCompleted
                        () -> {
                        }
                );
    }

    public <T> Disposable bindObservableConsumerWithProgress(Flowable<T> o,
                                                             Consumer<T> onNext) {

        materialDialog = new MaterialDialog.Builder(context)
                .content("Loading...")
                .progress(true, 0)
                .cancelListener(
                        dialog -> {
                            netProgress.onCancelProgress();
                        })
                .progressIndeterminateStyle(false)
                .show();
        //showProgressDialog(activity, 1);

        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext,
                        // onError, 异常统一处理
                        e -> {
                            if (e instanceof SocketTimeoutException
                                    || e instanceof ConnectException) {

                                Log.i("TAG", "网络中断，请检查您的网络状态");
                                ToastUtil.showLong(context, "网络中断，请检查您的网络状态");

                            } else {

                                Log.i("TAG", "error:" + e.getMessage());
                                ToastUtil.showLong(context, "error:" + e.getMessage());
                            }

                            if(materialDialog != null){
                                materialDialog.dismiss();
                            }
                            //dismissProgressDialog();
                        },
                        // onCompleted
                        () -> {
                            if(materialDialog != null){
                                materialDialog.dismiss();
                            }
                            //dismissProgressDialog();
                        }
                );
    }
}
