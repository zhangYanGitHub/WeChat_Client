package com.zhang.chat.main.menu.model;

import com.greendao.gen.FriendDao;
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
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class NewFriendStateModel extends NewFriendStateContract.Model {

    private final UserDao userDao;
    private final VerificationDao verificationDao;
    private final FriendDao friendDao;

    public NewFriendStateModel() {
        userDao = getSession().getUserDao();
        verificationDao = getSession().getVerificationDao();
        friendDao = getSession().getFriendDao();
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
        if (ListUtil.isNotEmpty(list)) {
            for (Verification verification : list) {
                long other_id = getOther_id(verification);
                final List<Friend> list1 = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(other_id)).list();
                if (ListUtil.isNotEmpty(list1)) {
                    final Friend friend = list1.get(0);
                    verification.setFriend_img_face(friend.getUser_img_face_path());
                    if (StrUtil.isBlank(verification.getFriend_name())) {
                        verification.setFriend_name(friend.getUser_name());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Friend getFriend(Verification verification) {
        long other_id = getOther_id(verification);
        final List<Friend> list1 = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(other_id)).list();
        if (ListUtil.isNotEmpty(list1)) {
            final Friend friend = list1.get(0);
            return friend;
        } else {
            return null;
        }
    }

    private long getOther_id(Verification verification) {
        if (verification == null) return -1;
        long m_id = Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME));
        if (m_id == verification.getFriend_user_id()) {
            return verification.getUser_friend_id();

        } else {
            return verification.getFriend_user_id();
        }
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
