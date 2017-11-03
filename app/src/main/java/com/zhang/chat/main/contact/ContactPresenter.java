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
        initData();
    }

    @Override
    public void initData() {
        getFriendList();
    }

    @Override
    public void getFriendList() {


        mModel.getFriendList().subscribe(new ApiSubscribe<ResList<Friend>>(context, TAG, 0, false) {
            @Override
            public void onSuccess(int whichRequest, ResList<Friend> friends) {
                mModel.addFriendToSql(friends.getList());
                mView.initData(friends.getList());
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
                refresh();
            }
        });
        mModel.getVerificationList().subscribe(new ApiSubscribe<ResList<Verification>>(context, TAG, 0, false) {
            @Override
            public void onSuccess(int whichRequest, ResList<Verification> verificationResList) {
                for (Verification v : verificationResList.getList()) {
                    mModel.addBySocket(v);
                }
                List<Verification> list = mModel.getList();
                mView.setNotice(0, list == null ? 0 : list.size());
                mView.setNotice(1, 0);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                AppToast.showToast(e.getMessage());
                List<Verification> list = mModel.getList();
                mView.setNotice(0, list == null ? 0 : list.size());
                mView.setNotice(1, 0);
            }

            @Override
            public void onComplete() {
                super.onComplete();
                AppLog.e(TAG + "---------->onComplete()");
            }
        });

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
