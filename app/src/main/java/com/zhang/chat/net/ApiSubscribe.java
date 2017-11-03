package com.zhang.chat.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.zhang.chat.app.ApiManager;
import com.zhang.chat.app.App;
import com.zhang.chat.R;
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
    private static ProgressDialog mDialog;

    public ApiSubscribe(Context context, String key, int whichRequest, boolean isShowDialog) {

        if (mDialog != null && mDialog.isShowing() && !((Activity) context).isFinishing()) {
            mDialog.dismiss();
        }
        this.context = context;
        this.key = key;
        this.whichRequest = whichRequest;
        this.isShowDialog = isShowDialog;
        mRxManager = ApiManager.get();
        mDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        mDialog.setIndeterminate(true);
        mDialog.setMessage("正在加载中");
    }

    @Override
    public void onSubscribe(Disposable d) {
        mRxManager.add(key, d);
        if (isShowDialog) {
            mDialog.show();
        }
        this.onStart(whichRequest);
    }

    protected void onStart(int whichRequest) {

    }

    @Override
    public final void onNext(T value) {
        if (!((Activity) context).isFinishing())
            onSuccess(whichRequest, value);
    }

    @Override
    public void onError(Throwable e) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }

        //网络
        if (!NetWorkUtils.isNetConnected(App.getApplication())) {
            onError(whichRequest, new RuntimeException(App.getApplication().getString(R.string.no_net)));
        }


        onError(whichRequest, e);
    }

    @Override
    public void onComplete() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    public abstract void onSuccess(int whichRequest, T t);

    public abstract void onError(int whichRequest, Throwable e);
}
