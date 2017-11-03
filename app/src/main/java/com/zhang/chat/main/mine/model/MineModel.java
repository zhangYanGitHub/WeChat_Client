package com.zhang.chat.main.mine.model;

import com.greendao.gen.UserDao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class MineModel extends MineContract.Model {


    private UserDao userDao;

    public MineModel() {
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

    @Override
    public Observable<String> upLoadMemFace(String path, String user_id) {
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(part);

        Map<String, String> map = new HashMap<String, String>();
        map.put("flag", "0");
        return RetrofitProvider
                .getUpLoadService(50)
                .upload(parts, map)
                .flatMap(new ApiFunction<String>())
                .compose(RxSchedulers.<String>io_main());
    }

    /**
     * @param type
     * @param user
     */
    @Override
    public void updateUserSQL(int type, User user) {
        ShareUtil.setPreferBool(Constant.UPDATE_USER_INFO);
        User user1 = getUserFromSQL();
        switch (type) {
            case MinePresenter.UPDATE_ACCOUNT:
                user1.setUser_account(user.getUser_account());
                break;

            case MinePresenter.UPDATE_DESC:
                user1.setUser_desc(user.getUser_desc());
                break;
            case MinePresenter.UPDATE_PASSWORD:
                user1.setUser_password(user.getUser_password());
                break;
            case MinePresenter.UPDATE_PHONE:
                user1.setUser_phone(user.getUser_phone());
                break;
            case MinePresenter.UPDATE_SEX:
                user1.setUser_sex(user.getUser_sex());
                break;
            case MinePresenter.UPDATE_NAME:
                user1.setUser_name(user.getUser_name());
                break;
            case MinePresenter.UPDATE_IMGFACEPATH:
                user1.setUser_img_face_path(user.getUser_img_face_path());
                break;

        }

        long l = userDao.insertOrReplace(user1);
        AppLog.i("updateUserSQL --->" + l);
        return;
    }

    @Override
    public Observable<User> updateUserNet(int type, User user) {
        HashMap<String, String> map = new HashMap<>();
        switch (type) {
            case MinePresenter.UPDATE_ACCOUNT:
                map.put("user_account", user.getUser_account());
                break;
            case MinePresenter.UPDATE_DESC:
                map.put("user_desc", user.getUser_desc());
                break;
            case MinePresenter.UPDATE_PASSWORD:
                map.put("user_password", user.getUser_password());
                break;
            case MinePresenter.UPDATE_PHONE:
                map.put("user_phone", user.getUser_phone());
                break;
            case MinePresenter.UPDATE_SEX:
                map.put("user_sex", user.getUser_sex() + "");
                break;
            case MinePresenter.UPDATE_NAME:
                map.put("user_name", user.getUser_name());
                break;
            case MinePresenter.UPDATE_IMGFACEPATH:
                map.put("user_img_face_path", user.getUser_img_face_path());
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
