package com.zhang.chat.main.chat.contract;

import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.netty.FutureListener;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public interface ChatContract {
    interface View extends BaseView<ChatContract.Presenter> {
        Friend getFriend();

        void notify(Message message);

        void notify(List<Message> message);
    }

    abstract class Presenter extends BasePresenter<ChatContract.View, ChatContract.Model> {
        /**
         * 发送消息
         *
         * @param message
         */
        public abstract void sendMessage(String message);

        public abstract void getMessage();

        public abstract void getResponse();

        public abstract User getUser();
    }

    abstract class Model extends BaseModel {
        public abstract User getUser();

        public abstract List<Message> getMessage(Long friendID);

        public abstract Message Message(Long friendID);

        public abstract void add(Message message);


        /**
         * 发送消息
         *
         * @param message
         * @param listener
         */
        public abstract void sendMessage(Message message, FutureListener listener);

    }
}
