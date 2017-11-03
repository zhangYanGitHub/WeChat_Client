package com.zhang.chat.main.menu.model;

import com.greendao.gen.UserDao;
import com.greendao.gen.VerificationDao;

import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.menu.contract.NewFriendStateContract;
import com.zhang.chat.main.menu.contract.VerificationContract;
import com.zhang.chat.netty.FutureListener;
import com.zhang.chat.netty.NettyClient;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class NewFriendStateModel extends NewFriendStateContract.Model {

    private final UserDao userDao;
    private final VerificationDao verificationDao;

    public NewFriendStateModel() {
        userDao = getSession().getUserDao();
       verificationDao = getSession().getVerificationDao();
    }


    @Override
    public void sendVerification(Verification verification, FutureListener futureListener) {

        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setBody(Serializer.serialize(verification));
        messageHolder.setSign(ProtocolHeader.REQUEST);
        messageHolder.setType(ProtocolHeader.ADD_FRIEND);
        NettyClient.getInstance().sendMsgToServer(messageHolder, futureListener);
        AppLog.e(messageHolder.toString());
    }

    @Override
    public List<Verification> getVerificationList() {
        List<Verification> list = verificationDao.queryBuilder().list();
        return list;
    }

    @Override
    public void update(Verification deserialize) {
        deserialize.setIsRead(true);
        List<Verification> list = verificationDao.queryBuilder().whereOr(VerificationDao.Properties.Friend_user_id.eq(deserialize.getFriend_user_id()),
                VerificationDao.Properties.User_friend_id.eq(deserialize.getUser_friend_id())).list();
        if (ListUtil.isEmpty(list)) {
            verificationDao.insertOrReplace(deserialize);
        } else {
            deserialize.setM_id(list.get(0).getM_id());
            verificationDao.update(deserialize);
        }

        AppLog.e(TAG + deserialize.toString());
    }
}
