package com.zhang.chat.main.contact;

import com.greendao.gen.FriendDao;
import com.greendao.gen.VerificationDao;

import java.util.ArrayList;
import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class ContactModel extends ContactContract.Model {
    private FriendDao friendDao;
    private VerificationDao verificationDao;

    public ContactModel() {
        friendDao = getSession().getFriendDao();
        verificationDao = getSession().getVerificationDao();
    }

    @Override
    public List<Friend> getFriendListFromSql() {
        List<Friend> list = friendDao.queryBuilder().build().list();

        return list;
    }

    @Override
    public Observable<ResList<Friend>> getFriendList() {
        return RetrofitProvider
                .getService().getFriendList()
                .flatMap(new ApiFunction<ResList<Friend>>())
                .compose(RxSchedulers.<ResList<Friend>>io_main());
    }
    @Override
    public Observable<ResList<Verification>> getVerificationList() {
        return RetrofitProvider
                .getService()
                .getVerificationList()
                .flatMap(new ApiFunction<ResList<Verification>>())
                .compose(RxSchedulers.<ResList<Verification>>io_main());
    }

    @Override
    public List<Verification> getList() {
        List<Verification> list = verificationDao.queryBuilder().where(VerificationDao.Properties.IsRead.eq(false)).list();
        return list;
    }

    @Override
    public void addFriendToSql(List<Friend> friends) {
        if (ListUtil.isEmpty(friends)) return;
        friendDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < friends.size(); i++) {
                    Friend note = friends.get(i);
                    friendDao.insertOrReplace(note);
                }
            }
        });
    }

    @Override
    public int updateNewFriend() {
        List<Verification> list = verificationDao.queryBuilder().where(VerificationDao.Properties.IsRead.eq(false)).list();
        if (ListUtil.isEmpty(list)) return 0;
        return list.size();
    }

    @Override
    public void setIsRead() {
        List<Verification> list = verificationDao.queryBuilder().where(VerificationDao.Properties.IsRead.eq(false)).list();
        if (ListUtil.isEmpty(list)) return;

        for (Verification verification : list) {
            verification.setIsRead(true);
            verificationDao.update(verification);
        }
    }
    @Override
    public void addBySocket(Verification deserialize) {
        List<Verification> list = verificationDao.queryBuilder().whereOr(VerificationDao.Properties.Friend_user_id.eq(deserialize.getFriend_user_id()),
                VerificationDao.Properties.User_friend_id.eq(deserialize.getUser_friend_id())).list();
        if (ListUtil.isEmpty(list)) {
            deserialize.setIsRead(false);
            verificationDao.insertOrReplace(deserialize);
        } else {
            deserialize.setIsRead(true);
            deserialize.setM_id(list.get(0).getM_id());
            verificationDao.update(deserialize);
        }

        AppLog.e(TAG + deserialize.toString());
    }

}
