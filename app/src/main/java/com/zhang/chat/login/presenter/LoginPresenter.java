package com.zhang.chat.login.presenter;

import com.zhang.chat.R;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.login.contract.LoginContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class LoginPresenter extends LoginContract.Presenter {


    @Override
    public void onStart() {
        String preferStr = ShareUtil.getPreferStr(Constant.USER_NAME);
        if (StrUtil.isBlank(preferStr)) return;
        User user = new User();
        user.setM_Id(Long.parseLong(preferStr));
        User user1 = mModel.queryUser(user);
    }


    @Override
    public void login() {
        String account = mView.getName();
        String password = mView.getPassword();

        if (StrUtil.isBlank(account)) {
            mView.showErrorMessage(context.getString(R.string.login_account_error_hint));
            return;
        }

        if (StrUtil.isBlank(password)) {
            mView.showErrorMessage(context.getString(R.string.login_error_password_hint));
            return;
        }
        mModel.login(account, password).subscribe(new ApiSubscribe<User>(context, TAG, 0, true) {
            @Override
            public void onSuccess(int whichRequest, User user) {
                if (user == null) {
                    mView.showErrorMessage("服务器返回数据为空");
                }
                //存入数据库
                user.setU_UserState(1);
                mModel.insert(user);
                ShareUtil.setPreferStr(Constant.USER_NAME, account);
                AppLog.i(user.toString());
                //登录成功处理
                GreenDaoManager.getInstance().initUserData();
                mView.update(user);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
                mView.showErrorMessage(e.getMessage());
            }
        });
    }


}
