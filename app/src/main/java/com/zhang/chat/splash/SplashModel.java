package com.zhang.chat.splash;

import com.greendao.gen.UserDao;

import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.utils.ListUtil;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class SplashModel extends SplashContract.SplashModel {

    private  UserDao userDao;

    public SplashModel(){
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




}
