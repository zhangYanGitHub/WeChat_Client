package com.zhang.chat.main.contact;

import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.chat.Verification;
import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/25.
 */

public interface ContactContract {
    interface View extends BaseView<ContactContract.Presenter> {
        void initData(List<Friend> friends);

        void setNotice(int type, int num);
    }

    abstract class Presenter extends BasePresenter<ContactContract.View, ContactContract.Model> {
        public abstract void initData();

        public abstract void getFriendList();

        public abstract void updateNewFriend();

        public abstract void setIsRead();

        public abstract void refresh();
    }

    abstract class Model extends BaseModel {
        public abstract List<Friend> getFriendListFromSql();

        public abstract Observable<ResList<Friend>> getFriendList();

        public abstract void addFriendToSql(List<Friend> friends);

        public abstract int updateNewFriend();

        public abstract void setIsRead();

        public abstract Observable<ResList<Verification>> getVerificationList();

        public abstract List<Verification> getList();

        public abstract void addBySocket(Verification v);
    }
}
