package com.zhang.chat.main.mine.model;

import com.greendao.gen.UserDao;

import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.main.mine.contract.SystemContract;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

/**
 * Create by ZhangYan on 2017/9/19.
 */

public class SystemModel extends SystemContract.Model {

    private final UserDao userDao;

    public SystemModel() {
        userDao = getUserDao();
    }

    /**
     * 清除登录数据
     */
    @Override
    public void clearPersonData() {
        List<User> list = userDao
                .queryBuilder()
                .where(UserDao.Properties.U_UserState.eq(1))
                .list();
        if (ListUtil.isEmpty(list) || list.size() != 1) {
            AppLog.e("数据库用户状态异常  IsPresenterUser == false");
            return;
        }
        User user = list.get(0);
        user.setU_UserState(0);
        userDao.update(user);
        ShareUtil.setPreferStr(Constant.COOKIES_TOKEN, "");
        ShareUtil.setPreferStr(Constant.USER_NAME, "");
    }

    @Override
    public List<User> getAllUser() {
        List<User> list = userDao.queryBuilder()
                .list();
        return list;
    }


}
