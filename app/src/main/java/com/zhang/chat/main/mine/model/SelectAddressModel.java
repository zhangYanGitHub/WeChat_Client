package com.zhang.chat.main.mine.model;

import com.greendao.gen.UserDao;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.contract.SelectAddressContract;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class SelectAddressModel extends SelectAddressContract.Model {


    private UserDao userDao;

    public SelectAddressModel() {
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

    /**
     */
    @Override
    public void updateAddressBySql(User user) {
        User user1 = getUserFromSQL();
        user1.setU_NationID(user.getU_NationID());
        user1.setU_Province(user.getU_Province());
        user1.setU_City(user.getU_City());
        user1.setAddress_message(user.getAddress_message());
        long l = userDao.insertOrReplace(user1);
        AppLog.i("updateUserSQL --->" + l);
        return;
    }


    @Override
    public Observable<User> updateUserNet(int type, User user) {
        HashMap<String, String> map = new HashMap<>();
        switch (type) {
            case MinePresenter.UPDATE_ADDRESS:
                map.put("u_NationID", user.getU_NationID());
                map.put("u_City", user.getU_City());
                map.put("u_Province", user.getU_Province());
                map.put("address_message", user.getAddress_message());
                break;


        }
        map.put("type", String.valueOf(type));
        return RetrofitProvider
                .getService()
                .updateUser(map)
                .flatMap(new ApiFunction<User>())
                .compose(RxSchedulers.<User>io_main());

    }

}
