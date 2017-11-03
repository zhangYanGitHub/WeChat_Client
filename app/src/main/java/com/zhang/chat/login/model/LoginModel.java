package com.zhang.chat.login.model;

import com.greendao.gen.UserDao;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.login.contract.LoginContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class LoginModel extends LoginContract.Model {


    private final UserDao userDao;

    public LoginModel() {
        userDao = getUserDao();
    }

    @Override
    public Observable<User> login(String name, String password) {

        return RetrofitProvider
                .getService()
                .login(name, password)
                .flatMap(new ApiFunction<User>())
                .compose(RxSchedulers.<User>io_main());
    }

    @Override
    public User queryUser(User user) {

        return user;
    }

    @Override
    public void insert(User user) {
        userDao.insertOrReplace(user);
    }


}
