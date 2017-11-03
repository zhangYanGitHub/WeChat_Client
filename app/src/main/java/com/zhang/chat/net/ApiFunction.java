package com.zhang.chat.net;

import com.greendao.gen.UserDao;
import com.google.gson.Gson;

import java.util.List;

import com.zhang.chat.app.AppManager;
import com.zhang.chat.base.BaseResponse;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.login.LoginActivity;
import com.zhang.chat.utils.AppToast;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class ApiFunction<T> implements Function<BaseResponse<T>, Observable<T>> {

    private Gson gson;

    public ApiFunction() {
        gson = new Gson();
    }

    @Override
    public Observable<T> apply(BaseResponse<T> json) throws Exception {
        if (json == null) return null;
        switch (json.getCode()) {
            case 100:
            case 101:
            case 102:
            case 103:
                throw new RuntimeException(json.getInfo());
            case 1000:
            case 1001:
                ShareUtil.setPreferStr(Constant.COOKIES_TOKEN, "");
                ShareUtil.setPreferStr(Constant.USER_NAME, "");
                UserDao userDao = GreenDaoManager.getInstance().getUserDaoSession().getUserDao();
                List<User> list = userDao.queryBuilder()
                        .where(UserDao.Properties.U_UserState.eq(1))
                        .list();
                if (ListUtil.isEmpty(list) || list.size() != 1) {
                    AppLog.e("数据库用户状态异常  IsPresenterUser == false");
                }
                User user = list.get(0);
                GreenDaoManager.getInstance().closeConnection();
                user.setU_UserState(0);
                userDao.update(user);
                LoginActivity.startAction(AppManager.getAppManager().currentActivity());
                AppManager.getAppManager().removeAllActivity();
                AppToast.showToast(json.getInfo());
            default:
                return flatResponse(json);
        }
    }

    private Observable<T> flatResponse(BaseResponse<T> json) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                e.onNext(json.getData());
                AppLog.e(json.getData().toString());
            }
        });
    }


}
