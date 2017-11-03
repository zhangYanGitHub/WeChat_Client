package com.zhang.chat.main.mine.contract;

import java.util.List;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.User;

/**
 * Create by ZhangYan on 2017/9/19.
 */

public interface SystemContract {

     interface View extends BaseView<SystemContract.Presenter> {
        void goToLogin();
    }

     abstract class Presenter extends BasePresenter<SystemContract.View, SystemContract.Model> {
        public abstract  void clearData();

         public abstract void exitApp();
     }

     abstract class Model extends BaseModel {

        public abstract void clearPersonData();

        public abstract List<User> getAllUser();

    }
}
