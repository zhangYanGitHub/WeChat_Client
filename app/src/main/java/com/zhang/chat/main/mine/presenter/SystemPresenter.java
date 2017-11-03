package com.zhang.chat.main.mine.presenter;

import com.zhang.chat.app.App;
import com.zhang.chat.app.AppManager;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.main.mine.contract.SystemContract;

/**
 * Create by ZhangYan on 2017/9/19.
 */

public class SystemPresenter extends SystemContract.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    public void clearData() {
        AppManager.getAppManager().removeAllExcuCurrentActivity();
        mModel.clearPersonData();
        GreenDaoManager.getInstance().closeConnection();
        mView.goToLogin();
    }

    @Override
    public void exitApp() {
        AppManager.getAppManager().AppExit(App.getApplication(), false);
    }
}
