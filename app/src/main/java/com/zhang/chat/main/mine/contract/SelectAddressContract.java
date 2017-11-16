package com.zhang.chat.main.mine.contract;


import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.User;

import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public interface SelectAddressContract {

    interface View extends BaseView<SelectAddressContract.Presenter> {
        /**
         * 定位完成
         *
         * @param country 国家
         * @param provice 省
         * @param city    市
         * @param county  详细位置
         */
        void locationFinish(String country, String provice, String city, String county);

        /**
         * 定位失败
         */
        void locationFail();
    }

    abstract class Presenter extends BasePresenter<SelectAddressContract.View, SelectAddressContract.Model> {
        /**
         * 启动定位
         */
        public abstract void startLocation();

        public abstract void updateUser(int type, User user);
    }

    abstract class Model extends BaseModel {
        /**
         * @return
         */
        public abstract User getUserFromSQL();

        /**
         *
         */
        public abstract void updateAddressBySql(User user);


        /**
         * @param type
         * @param user
         * @return
         */
        public abstract Observable<User> updateUserNet(int type, User user);
    }
}
