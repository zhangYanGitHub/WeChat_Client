package com.zhang.chat.main.chat.contract;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public interface FriendDataContract {
    interface View extends BaseView<FriendDataContract.Presenter> {
        void initFriendData();
    }

    abstract class Presenter extends BasePresenter<FriendDataContract.View, FriendDataContract.Model> {
        public abstract void getFriendById(long id);

    }

    abstract class Model extends BaseModel {
        public abstract Friend getFriendById(long id);

    }
}
