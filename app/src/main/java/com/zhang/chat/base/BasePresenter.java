package com.zhang.chat.base;

import android.content.Context;

import com.zhang.chat.app.ApiManager;

/**
 * Created by 张俨 on 2017/9/7.
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected String TAG;
    protected V mView;
    public Context context;
    protected ApiManager manager;
    protected M mModel;

    public void setVM(V v, M m) {
        this.mView = v;
        manager = ApiManager.get();
        this.mModel = m;
        TAG = getClass().getName();
        this.onStart();
    }

    public abstract void onStart();

    public  void onDestroy(){
        manager.remove(TAG);
    };


}
