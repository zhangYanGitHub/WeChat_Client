package com.zhang.chat.login.model;

import com.greendao.gen.UserDao;

import java.util.HashMap;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.login.contract.RegisterContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class RegisterModel extends RegisterContract.Model {


    private final UserDao userDao;

    public RegisterModel() {
        userDao = getUserDao();
    }


    @Override
    public void insert(User user) {
        userDao.insertOrReplace(user);
    }

    @Override
    public Observable<User> register(User user) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_name", user.getUser_name());
        map.put("user_password", user.getUser_password());
        map.put("user_phone", user.getUser_phone());
        map.put("user_real_name", user.getUser_real_name());
        map.put("user_sex", user.getUser_sex()+"");
        return RetrofitProvider.getService()
                .register(map).flatMap(new ApiFunction<User>())
                .compose(RxSchedulers.<User>io_main());
    }


}
