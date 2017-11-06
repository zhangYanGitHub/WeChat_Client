package com.zhang.chat.login.presenter;

import com.zhang.chat.bean.User;
import com.zhang.chat.login.contract.RegisterContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class RegisterPresenter extends RegisterContract.Presenter {


    @Override
    public void onStart() {

    }


    @Override
    public void register() {
        String name = mView.getName();
        String realName = mView.getRealName();
        String password = mView.getPassword();
        String confirmPassword = mView.getConfirmPassword();
        String phone = mView.getPhone();
        int sex = mView.getSex();
        if (StrUtil.isBlank(name)) {
            mView.showErrorMessage("昵称不能为空");
            return;
        }
        if (StrUtil.isBlank(realName)) {
            mView.showErrorMessage("姓名不能为空");
            return;
        }
        if (StrUtil.isBlank(password)) {
            mView.showErrorMessage("密码不能为空");
            return;
        }
        if (StrUtil.isBlank(confirmPassword)) {
            mView.showErrorMessage("确认密码不能为空");
            return;
        }
        if (StrUtil.isBlank(phone)) {
            mView.showErrorMessage("手机号码不能为空");
            return;
        }
        if (!password.equals(confirmPassword)) {
            mView.showErrorMessage("两次密码输入不一致");
            return;
        }
        User user = new User();
        user.setUser_sex(sex);
        user.setUser_real_name(realName);
        user.setUser_password(password);
        user.setUser_name(name);
        user.setUser_phone(phone);

        mModel.register(user).subscribe(new ApiSubscribe<User>(context, TAG, 0, false) {
            @Override
            public void onSuccess(int whichRequest, User user) {
                if (user == null) mView.showErrorMessage("服务器返回数据为空");
                mView.sucess(user);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                mView.showErrorMessage(e.getMessage());
            }
        });

    }


}
