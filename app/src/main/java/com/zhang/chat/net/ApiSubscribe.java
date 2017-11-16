package com.zhang.chat.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.zhang.chat.app.ApiManager;
import com.zhang.chat.app.App;
import com.zhang.chat.R;
import com.zhang.chat.app.AppManager;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.utils.NetWorkUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 张俨 on 2017/9/11.
 */

public abstract class ApiSubscribe<T> implements Observer<T> {

    private Context context;
    private String key;
    private int whichRequest;
    private boolean isShowDialog;
    private ApiManager mRxManager;
    private ProgressDialog mDialog;

    public ApiSubscribe(Context context, String key, int whichRequest, boolean isShowDialog) {


        this.context = context;
        this.key = key;
        this.whichRequest = whichRequest;
        this.isShowDialog = isShowDialog;
        mRxManager = ApiManager.get();

    }

    @Override
    public void onSubscribe(Disposable d) {
        mRxManager.add(key, d);
        BaseActivity baseActivity = AppManager.getAppManager().currentActivity();
        if ( isShowDialog && baseActivity.getClass().equals(((BaseActivity) context).getClass()) ) {
            baseActivity.showDialog();
        } else {
        }
        this.onStart(whichRequest);
    }

    protected void onStart(int whichRequest) {

    }

    @Override
    public final void onNext(T value) {
        if (!((Activity) context).isFinishing())
            onSuccess(whichRequest, value);
        BaseActivity baseActivity = AppManager.getAppManager().currentActivity();
        if (isShowDialog && baseActivity.getClass().equals(((BaseActivity) context).getClass())) {
            baseActivity.dismissDialog();
        } else {
        }
    }

    @Override
    public void onError(Throwable e) {

        //网络
        if (!NetWorkUtils.isNetConnected(App.getApplication())) {
            onError(whichRequest, new RuntimeException(App.getApplication().getString(R.string.no_net)));
        }
        BaseActivity baseActivity = AppManager.getAppManager().currentActivity();
        if (isShowDialog && baseActivity.getClass().equals(((BaseActivity) context).getClass())) {
            baseActivity.dismissDialog();
        } else {
        }

        onError(whichRequest, e);
    }

    @Override
    public void onComplete() {
        BaseActivity baseActivity = AppManager.getAppManager().currentActivity();
        if (isShowDialog && baseActivity.getClass().equals(((BaseActivity) context).getClass())) {
            baseActivity.dismissDialog();
        } else {
        }
    }


    public abstract void onSuccess(int whichRequest, T t);

    public abstract void onError(int whichRequest, Throwable e);
}
