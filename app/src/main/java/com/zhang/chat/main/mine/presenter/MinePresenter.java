package com.zhang.chat.main.mine.presenter;

import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.net.ApiSubscribe;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class MinePresenter extends MineContract.Presenter {

    public static final int UPDATE_NAME = 1;
    public static final int UPDATE_ACCOUNT = 2;
    public static final int UPDATE_SEX = 3;
    public static final int UPDATE_IMGFACEPATH =4;
    public static final int UPDATE_PHONE = 5;
    public static final int UPDATE_PASSWORD = 6;
    public static final int UPDATE_DESC = 7;
    public static final int UPDATE_ADDRESS = 8;

    @Override
    public void onStart() {
        AppLog.e(this.getClass().getName() + " onStart()");
        mView.updateUserInfo(mModel.getUserFromSQL());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更新用户信息
     *
     * @param type
     * @param user
     */
    @Override
    public void updateUser(final int type, final User user) {
        mModel.updateUserNet(type, user).subscribe(new ApiSubscribe<User>(context, TAG, 0, false) {
            @Override
            public void onSuccess(int whichRequest, User user1) {
                AppLog.e("============================");
                mModel.updateUserSQL(type, user);
                //更新UI
                mView.updateUserInfo(type, user);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void uploadFile(String path, String user_id) {
        mModel.upLoadMemFace(path, user_id).subscribe(new ApiSubscribe<String>(context, TAG, 1, false) {
            @Override
            public void onSuccess(int whichRequest, String image) {
                updateImage(image, user_id);
            }

            @Override
            public void onError(int whichRequest, Throwable e) {
                e.printStackTrace();
            }
        });
    }

    private void updateImage(String image, String user_id) {

        User userFromSQL = new User();
        userFromSQL.setM_Id(Long.parseLong(user_id));
        userFromSQL.setUser_img_face_path(image);
        this.updateUser(MinePresenter.UPDATE_IMGFACEPATH, userFromSQL);
    }
}
