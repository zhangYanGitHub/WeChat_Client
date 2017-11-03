package com.zhang.chat.main.menu.model;

import com.greendao.gen.UserDao;
import com.greendao.gen.VerificationDao;

import java.util.List;

import com.zhang.chat.app.App;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.menu.contract.SearchFriendContract;
import com.zhang.chat.main.menu.contract.VerificationContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.netty.FutureListener;
import com.zhang.chat.netty.NettyClient;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;
import io.reactivex.Observable;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class VerificationModel extends VerificationContract.Model {

    private final UserDao userDao;
    private final VerificationDao verificationDao;

    public VerificationModel() {
        userDao = getSession().getUserDao();
        verificationDao = getSession().getVerificationDao();
    }


    @Override
    public User getUser() {
        List<User> list = userDao.queryBuilder().where(UserDao.Properties.M_Id.eq(Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)))).list();
        if (ListUtil.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public void sendVerification(Friend friend, String message) {
        Verification verification = new Verification();
        verification.setM_state(1);
        verification.setFriend_user_id(friend.getUser_id());
        verification.setUser_friend_id(Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)));
        verification.setMessage(message);


        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setBody(Serializer.serialize(verification));
        messageHolder.setSign(ProtocolHeader.REQUEST);
        messageHolder.setType(ProtocolHeader.ADD_FRIEND);
        NettyClient.getInstance().sendMsgToServer(messageHolder, new FutureListener() {
            @Override
            public void success() {
                verificationDao.insertOrReplace(verification);
            }

            @Override
            public void error() {

            }
        });
        AppLog.e(messageHolder.toString());
    }
}
