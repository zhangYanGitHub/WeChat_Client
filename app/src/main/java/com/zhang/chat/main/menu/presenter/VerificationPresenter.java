package com.zhang.chat.main.menu.presenter;

import com.zhang.chat.bean.User;
import com.zhang.chat.main.menu.contract.VerificationContract;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class VerificationPresenter extends VerificationContract.Presenter {
    @Override
    public void onStart() {

    }


    @Override
    public void sendVerification() {
        mModel.sendVerification(mView.getFriend(),mView.getMessage());
    }

    @Override
    public User getUser() {
        return mModel.getUser();
    }
}
