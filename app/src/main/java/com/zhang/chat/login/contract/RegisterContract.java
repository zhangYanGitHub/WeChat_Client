package com.zhang.chat.login.contract;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.User;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/7.
 */

public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        /**
         * 获取信息
         */
        String getName();

        String getRealName();

        String getPassword();

        String getConfirmPassword();

        int getSex();

        String getPhone();


        void sucess(User user);

        /**
         * @param error
         */
        void showErrorMessage(String error);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void register();


    }

    abstract class Model extends BaseModel {

        public abstract void insert(User user);

        public abstract Observable<User> register(User user);
    }
}
