package com.zhang.chat.main.contact;

import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.AppToast;


/**
 * Create by ZhangYan on 2017/9/25.
 */

public class ContactPresenter extends ContactContract.Presenter {
    @Override
    public void onStart() {
    }

    @Override
    public void initData() {
        getFriendList();
    }

    @Override
    public void getFriendList() {
        refresh();

    }

    public void refresh() {
        List<Friend> listFromSql = mModel.getFriendListFromSql();
        mView.initData(listFromSql);

    }

    @Override
    public void updateNewFriend() {
        mModel.updateNewFriend();
    }

    @Override
    public void setIsRead() {
        mModel.setIsRead();
        List<Verification> list = mModel.getList();
        mView.setNotice(0, list == null ? 0 : list.size());
    }
}
