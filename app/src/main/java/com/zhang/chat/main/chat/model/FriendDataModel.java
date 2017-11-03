package com.zhang.chat.main.chat.model;

import com.greendao.gen.FriendDao;

import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.main.chat.contract.FriendDataContract;
import com.zhang.chat.utils.ListUtil;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class FriendDataModel extends FriendDataContract.Model {

    private final FriendDao friendDao;

    public FriendDataModel() {
        friendDao = getSession().getFriendDao();
    }

    @Override
    public Friend getFriendById(long id) {
        List<Friend> list = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(id)).build().list();
        if (ListUtil.isNotEmpty(list) && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }
}
