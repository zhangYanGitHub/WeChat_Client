package com.zhang.chat.main.menu.presenter;

import java.util.ArrayList;
import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.main.menu.contract.NewFriendStateContract;
import com.zhang.chat.netty.FutureListener;
import com.zhang.chat.utils.ListUtil;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class NewFriendStatePresenter extends NewFriendStateContract.Presenter {

    private List<Verification> verifications = new ArrayList<>();


    @Override
    public void onStart() {

    }


    @Override
    public void sendVerification(Verification friend) {
        mModel.sendVerification(friend, new FutureListener() {
            @Override
            public void success() {
                mModel.update(friend);
                mView.notifyList();
            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public void getVerificationList() {
        List<Verification> verificationList = mModel.getVerificationList();
        if (ListUtil.isEmpty(verificationList)) {
            return;
        }
        verifications.clear();
        verifications.addAll(verificationList);
        mView.notifyList();
    }

    @Override
    public List<Verification> getList() {
        return verifications;
    }

    @Override
    public Friend getFriend(Verification verification) {
        return mModel.getFriend(verification);
    }

    @Override
    public void setState(int position) {
        Verification verification = verifications.get(position);
        verification.setM_state(2);
        sendVerification(verification);
    }

}
