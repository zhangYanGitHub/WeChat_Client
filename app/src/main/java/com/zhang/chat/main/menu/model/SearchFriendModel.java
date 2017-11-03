package com.zhang.chat.main.menu.model;

import com.greendao.gen.UserDao;

import java.util.List;

import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.User;
import com.zhang.chat.main.menu.contract.SearchFriendContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class SearchFriendModel extends SearchFriendContract.Model {

    private final UserDao userDao;

    public SearchFriendModel() {
        userDao = getSession().getUserDao();
    }

    @Override
    public Observable<ResList<User>> searchFriend(String key, int type) {
        return RetrofitProvider
                .getService()
                .SearchFriend(key, type)
                .flatMap(new ApiFunction<ResList<User>>())
                .compose(RxSchedulers.<ResList<User>>io_main());
    }

    @Override
    public void save(List<User> list) {
        for (User user : list) {
            userDao.insertOrReplace(user);
        }
    }


}
