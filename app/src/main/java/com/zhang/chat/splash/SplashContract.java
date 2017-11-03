package com.zhang.chat.splash;

import com.zhang.chat.base.BaseModel;
import com.zhang.chat.base.BasePresenter;
import com.zhang.chat.base.BaseView;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;

import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/7.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter> {
        void jumpToLogin();

        void jumpToMain();
    }

    abstract class Presenter extends BasePresenter<View, SplashModel> {
        public abstract boolean isNeedUpdate();

        public abstract void downloadAPK();
    }

    abstract class SplashModel extends BaseModel {
        public abstract User getUserFromSQL();

        public abstract Observable<MainData> getUserData(long m_id);

        public abstract void save(MainData mainData);
    }

}
