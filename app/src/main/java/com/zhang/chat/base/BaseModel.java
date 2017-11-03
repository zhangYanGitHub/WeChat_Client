package com.zhang.chat.base;

import com.greendao.gen.DaoSession;
import com.greendao.gen.UserDao;

import com.zhang.chat.db.GreenDaoManager;

/**
 * Created by 张俨 on 2017/9/7.
 */

public abstract class BaseModel {
    protected final String TAG = this.getClass().getName();

    public BaseModel() {

    }

    protected UserDao getUserDao() {
        return GreenDaoManager.getInstance().getDaoMaster().getUserDao();
    }

    protected DaoSession getSession() {
        return GreenDaoManager.getInstance().getUserDaoSession();
    }
}
