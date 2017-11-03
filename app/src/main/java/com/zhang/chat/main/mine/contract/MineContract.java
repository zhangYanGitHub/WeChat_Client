package com.zhang.chat.main.mine.contract;


import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.User;
import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public interface MineContract {

    interface View extends BaseView<MineContract.Presenter> {
        /**
         * 更新用户信息
         */
        void updateUserInfo(User user);
        /**
         * 更新用户信息
         */
        void updateUserInfo(int type ,User user);
    }

    abstract class Presenter extends BasePresenter<MineContract.View, MineContract.Model> {

        public abstract void updateUser(int type,User user);

        public abstract void uploadFile(String path,String user_id);
    }

    abstract class Model extends BaseModel {

        public abstract User getUserFromSQL();

        public abstract Observable<String> upLoadMemFace(String path,String user_id);

        public abstract void updateUserSQL(int type,User user);

        public abstract Observable<User> updateUserNet(int type,User user);
    }
}
