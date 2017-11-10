package com.zhang.chat.login.presenter;

import com.zhang.chat.R;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.login.contract.LoginContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import com.zhang.chat.utils.StrUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class LoginPresenter extends LoginContract.Presenter {


    @Override
    public void onStart() {
        String preferStr = ShareUtil.getPreferStr(Constant.USER_NAME);
        if (StrUtil.isBlank(preferStr)) return;
        User user = new User();
        user.setM_Id(Long.parseLong(preferStr));
        User user1 = mModel.queryUser(user);
    }


    @Override
    public void login() {
        String account = mView.getName();
        String password = mView.getPassword();

        if (StrUtil.isBlank(account)) {
            mView.showErrorMessage(context.getString(R.string.login_account_error_hint));
            return;
        }

        if (StrUtil.isBlank(password)) {
            mView.showErrorMessage(context.getString(R.string.login_error_password_hint));
            return;
        }
        mModel.login(account, password).subscribe(new ApiSubscribe<MainData>(context, TAG, 0, true) {
            @Override
            public void onSuccess(int whichRequest, MainData mainData) {
                jump(mainData);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
                mView.showErrorMessage("登录异常 : " + e.getMessage());
            }
        });

    }


    private void jump(MainData mainData) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                if (mainData != null) {
                    ShareUtil.setPreferStr(Constant.USER_NAME, String.valueOf(mainData.getUser().getM_Id()));
                    mainData.getUser().setU_UserState(1);
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
                        mView.update();
                        String name = Thread.currentThread().getName();
                        AppLog.e(TAG + "  jump()  onNext() thread_name == " + name);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
