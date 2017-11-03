package com.zhang.chat.login.contract;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.User;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/7.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        /**
         * 获取登录信息
         */
        String getName();

        String getPassword();

        void update(User data);

        /**
         * 登录结果提示
         *
         * @param error
         */
        void showErrorMessage(String error);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void login();



    }

    abstract class Model extends BaseModel {
        public abstract User queryUser(User user);

        public abstract void insert(User user);

        public abstract Observable<User> login(String name, String password);
    }
}
