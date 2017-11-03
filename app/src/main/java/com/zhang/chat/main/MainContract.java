package com.zhang.chat.main;


import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.Verification;

/**
 * Created by 张俨 on 2017/9/7.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {


        /**
         * 设置微信通知数量
         *
         * @param number
         */
        void setDotNumber(int index, int number);

        MainModel getMainModel();

        void setTitleNum(int num);
    }

    abstract class Presenter extends BasePresenter<View, MainModel> {

        public abstract void refresh();
    }

    abstract class MainModel extends BaseModel {

        public abstract void add(Message message);

        public abstract void addBySocket(Message message);

        public abstract void add(ListMessage messages);

        public abstract void addBySocket(ListMessage messages);

        public abstract void addBySocket(Verification deserialize);

        public abstract void addBySocket(Friend deserialize);


        public abstract int[] getNotice();
    }

}
