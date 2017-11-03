package com.zhang.chat.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.utils.ShareUtil;


/**
 * Created by 张俨 on 2017/9/7.
 */

public class App extends Application {
    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();

        ShareUtil.init(this);
        application = this;
        initDataBase();
    }

    private void initDataBase() {
        GreenDaoManager.getInstance().initDataBase();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static App getApplication() {
        return application;
    }


}
