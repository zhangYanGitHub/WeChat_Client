package com.zhang.chat.splash;


import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.net.ApiSubscribe;

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
                    mModel.save(mainData);
                    mView.jumpToMain();
                }

                @Override
                public void onError(int whichRequest, Throwable e) {

                    mView.jumpToMain();
                }
            });

        }

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
