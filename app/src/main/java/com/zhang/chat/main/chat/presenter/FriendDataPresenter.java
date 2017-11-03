package com.zhang.chat.main.chat.presenter;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.main.chat.contract.FriendDataContract;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class FriendDataPresenter extends FriendDataContract.Presenter {


    @Override
    public void getFriendById(long id) {
        Friend friendById = mModel.getFriendById(id);
        if (friendById != null) {
            mView.initFriendData();
        }
    }

    @Override
    public void onStart() {

    }
}
