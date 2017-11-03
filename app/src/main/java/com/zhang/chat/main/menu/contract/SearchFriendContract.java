package com.zhang.chat.main.menu.contract;


import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.User;
import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public interface SearchFriendContract {

    interface View extends BaseView<SearchFriendContract.Presenter> {
        void updateResult(List<User> userList);

        void updateEmpty();
    }

    abstract class Presenter extends BasePresenter<SearchFriendContract.View, SearchFriendContract.Model> {
        public abstract void searchFriend(String key);

    }

    abstract class Model extends BaseModel {
        public abstract Observable<ResList<User>> searchFriend(String key , int type);


        public abstract void save(List<User> list);
    }
}
