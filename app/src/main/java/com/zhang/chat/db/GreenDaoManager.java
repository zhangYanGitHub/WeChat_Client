package com.zhang.chat.db;

import android.database.sqlite.SQLiteDatabase;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import com.zhang.chat.app.App;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/10/18.
 */

public class GreenDaoManager {
    private static final String TAG = GreenDaoManager.class.getName();
    private static final String DB_NAME = "common_user_db";

    //多线程中要被共享的使用volatile关键字修饰
    //用户数据库
    private static  volatile GreenDaoManager instance;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    //用户个人信息数据库
    private DaoMaster.DevOpenHelper myHelper;
    private DaoMaster myDaoMaster;
    private DaoSession myDaoSession;
    private String user_id = null;

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            synchronized (GreenDaoManager.class) {
                if (instance == null) {
                    instance = new GreenDaoManager();
                }
            }
        }
        return instance;
    }


    public void initDataBase() {
        mHelper = new DaoMaster.DevOpenHelper(App.getApplication(), DB_NAME, null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        initUserData();
    }

    public synchronized void initUserData() {
        user_id = ShareUtil.getPreferStr(Constant.USER_NAME);
        if (myHelper == null && StrUtil.isNotBlank(user_id)) {
            String db_name = String.valueOf("user_" + user_id + "_db");
            myHelper = new DaoMaster.DevOpenHelper(App.getApplication(), db_name, null);
            SQLiteDatabase db = myHelper.getWritableDatabase();
            // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
            myDaoMaster = new DaoMaster(db);
            myDaoSession = myDaoMaster.newSession();
            AppLog.e(TAG + "   initUserData ()");
        }

    }

    public DaoSession getUserDaoSession() {
        return this.myDaoSession;
    }

    public DaoSession getDaoMaster() {

        return mDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
        AppLog.e(TAG + "   closeConnection ()");
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeUserConnection() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
        AppLog.e(TAG + "   closeUserConnection ()");
    }

    public void closeHelper() {
        if (myHelper != null) {
            myHelper.close();
            myHelper = null;
        }
    }

    public void closeDaoSession() {
        if (myDaoSession != null) {
            myDaoSession.clear();
            myDaoSession = null;
        }
    }

}
