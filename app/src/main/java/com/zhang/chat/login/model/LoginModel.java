package com.zhang.chat.login.model;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import com.greendao.gen.VerificationDao;
import com.zhang.chat.app.App;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.login.contract.LoginContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.ListUtil;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class LoginModel extends LoginContract.Model {


    private final UserDao userDao;

    private MessageListDao messageListDao;
    private VerificationDao verificationDao;
    private FriendDao friendDao;

    public LoginModel() {
        userDao = getUserDao();
        if (getSession() != null) {
            messageListDao = getSession().getMessageListDao();
            friendDao = getSession().getFriendDao();
            verificationDao = getSession().getVerificationDao();
        }
    }

    @Override
    public Observable<MainData> login(String name, String password) {

        return RetrofitProvider
                .getService()
                .login(name, password)
                .flatMap(new ApiFunction<MainData>())
                .compose(RxSchedulers.<MainData>io_main());
    }

    @Override
    public User queryUser(User user) {

        return user;
    }

    @Override
    public void insert(User user) {
        userDao.insertOrReplace(user);
    }

    @Override
    public void save(MainData mainData) {

        messageListDao = getSession().getMessageListDao();
        friendDao = getSession().getFriendDao();
        verificationDao = getSession().getVerificationDao();

        List<Friend> friends = mainData.getFriends();
        List<MainData.MessageList> latestMessage = mainData.getLatestMessage();
        User user = mainData.getUser();
        List<Verification> verifications = mainData.getVerifications();

        if (ListUtil.isNotEmpty(friends)) {
            for (Friend friend : friends) {
                friendDao.insertOrReplace(friend);
            }
        }
        userDao.insertOrReplace(user);

        if (ListUtil.isNotEmpty(verifications)) {
            for (Verification verification : verifications) {
                verificationDao.insertOrReplace(verification);
            }
        }

        if (ListUtil.isNotEmpty(latestMessage)) {

            for (MainData.MessageList messageList : latestMessage) {
                messageListDao.deleteAll();
                MessageList messageList1 = new MessageList(messageList.getMessage(), messageList.getNumber());
                messageListDao.insertOrReplace(messageList1);
            }
        }
    }
}
