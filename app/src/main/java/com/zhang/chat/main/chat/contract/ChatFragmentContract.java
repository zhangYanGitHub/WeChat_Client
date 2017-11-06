package com.zhang.chat.main.chat.contract;

import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public interface ChatFragmentContract {
    interface View extends BaseView<ChatFragmentContract.Presenter> {
        void notifyList();
    }

    abstract class Presenter extends BasePresenter<ChatFragmentContract.View, ChatFragmentContract.Model> {
        public abstract void setList(List<MessageList> messages);

        public abstract void refresh();

        public abstract Friend getFriend(Long f_id);

        public abstract void getMessageList();

        public abstract void update(MessageList stringLocalChat);
    }

    abstract class Model extends BaseModel {
        public abstract List<MessageList> getLatestMessage();

        public abstract Friend getFriend(Long f_id);

        public abstract void start();

        public abstract Observable<ListMessage> getMessageList();



        public abstract void update(MessageList stringLocalChat);
    }
}
