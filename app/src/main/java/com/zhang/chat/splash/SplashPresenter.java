package com.zhang.chat.splash;


import com.zhang.chat.app.App;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 张俨 on 2017/9/7.
 */

public class SplashPresenter extends SplashContract.Presenter {


    @Override
    public void onStart() {
        User user = mModel.getUserFromSQL();
        if (mModel.getUserFromSQL() == null) {
            mView.jumpToLogin();
        } else {
            mModel.getUserData(user.getM_Id()).subscribe(new ApiSubscribe<MainData>(context, TAG, 0, false) {
                @Override
                public void onSuccess(int whichRequest, MainData mainData) {
                    GreenDaoManager.getInstance().initUserData();
                    jump(mainData);

                }

                @Override
                public void onError(int whichRequest, Throwable e) {
                    GreenDaoManager.getInstance().initUserData();
                    e.printStackTrace();
                    jump(null);
                }
            });

        }

    }

    private void jump(MainData mainData) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                if (mainData != null) {
                    GreenDaoManager.getInstance().initUserData();
                    mModel.save(mainData);
                }
                String name = Thread.currentThread().getName();
                AppLog.e(TAG + "  jump()  subscribe() thread_name == " + name);
                e.onNext("");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        mView.jumpToMain();
                        AppLog.e(TAG + "  jump()  onNext()");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isNeedUpdate() {
        return false;
    }

    @Override
    public void downloadAPK() {

    }

}
