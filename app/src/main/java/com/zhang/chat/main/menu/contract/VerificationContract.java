package com.zhang.chat.main.menu.contract;


import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.netty.FutureListener;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public interface VerificationContract {

    interface View extends BaseView<VerificationContract.Presenter> {
        Friend getFriend();

        String getMessage();
    }

    abstract class Presenter extends BasePresenter<VerificationContract.View, VerificationContract.Model> {

        /**
         * 发送消息
         */
        public abstract void sendVerification();

        public abstract User getUser();
    }

    abstract class Model extends BaseModel {


        public abstract User getUser();

        /**
         * 发送消息
         */
        public abstract void sendVerification(Friend friend,String message);


    }
}
