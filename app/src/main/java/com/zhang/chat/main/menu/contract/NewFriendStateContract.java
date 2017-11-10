package com.zhang.chat.main.menu.contract;


import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.netty.FutureListener;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public interface NewFriendStateContract {

    interface View extends BaseView<NewFriendStateContract.Presenter> {
        void notifyList();

    }

    abstract class Presenter extends BasePresenter<NewFriendStateContract.View, NewFriendStateContract.Model> {

        /**
         * 发送消息
         */
        public abstract void sendVerification(Verification friend);

        public abstract void getVerificationList();

        public abstract List<Verification> getList();

        public abstract Friend getFriend(Verification verification);

        public abstract void setState(int position);
    }

    abstract class Model extends BaseModel {


        /**
         * 发送消息
         */
        public abstract void sendVerification(Verification friend, FutureListener futureListener);

        public abstract List<Verification> getVerificationList();

        public abstract Friend getFriend(Verification verification);

        public abstract void update(Verification friend);
    }
}
