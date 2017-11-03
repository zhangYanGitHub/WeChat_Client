package com.zhang.chat.splash;

import com.greendao.gen.UserDao;

import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.ListUtil;

import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class SplashModel extends SplashContract.SplashModel {

    private UserDao userDao;

    public SplashModel() {
        userDao = getUserDao();
    }

    @Override
    public User getUserFromSQL() {
        List<User> list = userDao
                .queryBuilder()
                .where(UserDao.Properties.U_UserState.eq(1))
                .list();
        if (ListUtil.isEmpty(list)) return null;
        User user = list.get(0);
        return user;
    }

    @Override
    public Observable<MainData> getUserData(long m_id) {
        return RetrofitProvider.getService().getUserData(String.valueOf(m_id)).flatMap(new ApiFunction<MainData>()).compose(RxSchedulers.io_main());
    }

    @Override
    public void save(MainData mainData) {

    }


}
