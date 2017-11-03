package com.zhang.chat.splash;

/**
 * Created by 张俨 on 2017/9/7.
 */

public class SplashPresenter extends SplashContract.Presenter {


    @Override
    public void onStart() {
        if (mModel.getUserFromSQL() == null) {
            mView.jumpToLogin();
        } else {

            mView.jumpToMain();

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
