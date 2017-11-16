package com.zhang.chat.main.chat.model;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.chat.contract.ChatContract;
import com.zhang.chat.netty.FutureListener;
import com.zhang.chat.netty.NettyClient;
import com.zhang.chat.netty.protocol.MessageHolder;
import com.zhang.chat.netty.protocol.ProtocolHeader;
import com.zhang.chat.netty.serializer.Serializer;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class ChatModel extends ChatContract.Model {

    private final FriendDao friendDao;
    private final UserDao userDao;
    private final MessageDao messageDao;
    private final MessageListDao listDao;

    public ChatModel() {
        friendDao = getSession().getFriendDao();
        userDao = getUserDao();
        messageDao = getSession().getMessageDao();
        listDao = getSession().getMessageListDao();
    }


    @Override
    public User getUser() {
        List<User> list = userDao.queryBuilder().where(UserDao.Properties.M_id.eq(Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)))).list();

        if (ListUtil.isEmpty(list)) return null;
        User user = list.get(0);
        user.setUser_password("");
        return user;
    }

    @Override
    public List<Message> getMessage(Long friendID) {
        QueryBuilder<Message> qb = messageDao.queryBuilder();
        qb.whereOr(MessageDao.Properties.M_ToUserID.eq(friendID), MessageDao.Properties.M_FromUserID.eq(friendID))
                .orderAsc(MessageDao.Properties.M_Time);

        List<Message> list = qb.list();
        for (Message message : list) {
            message.setRead(true);
            messageDao.update(message);

        }
        return list;

    }

    @Override
    public Message Message(Long friendID) {
        QueryBuilder<Message> qb = messageDao.queryBuilder();
        qb.whereOr(MessageDao.Properties.M_FromUserID.eq(friendID), MessageDao.Properties.M_ToUserID.eq(friendID)).orderAsc(MessageDao.Properties.M_Time);
        List<Message> list = qb.list();
        if (ListUtil.isEmpty(list)) return null;
        list.get(list.size() - 1).setRead(true);
        return list.get(list.size() - 1);
    }

    @Override
    public void add(Message message) {
        message.setM_ID(null);
        message.setIsRead(true);
        String key = message.getKey();
        long insert = messageDao.insert(message);


        List<MessageList> list = listDao.queryBuilder().where(MessageListDao.Properties.KeyFlag.eq(key)).list();
        long friend_id;
        if (message.getM_FromUserID() == Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME))) {
            friend_id = message.getM_ToUserID();
        } else {
            friend_id = message.getM_FromUserID();
        }
        List<Friend> list1 = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(friend_id)).list();
        String img = null;
        String name = null;
        if (ListUtil.isEmpty(list1)) {
            AppLog.e(TAG + "add(Message message) list == null");
        } else {
            Friend friend = list1.get(0);
            img = friend.getUser_img_face_path();
            name = friend.getUser_name();
        }
        if (ListUtil.isEmpty(list)) {
            MessageList messageList = new MessageList(0, message.getM_PostMessages(),
                    message.getM_status(), message.getM_Time(), message.getM_MessagesTypeID(), message.getM_ToUserID(), message.getM_FromUserID()
                    , 0, key);
            messageList.setFriend_Name(name);
            messageList.setFriend_img_face(img);
            listDao.insert(messageList);
        } else {
            Long m_id = list.get(0).getM_ID();
            MessageList messageList = new MessageList(m_id, message.getM_PostMessages(),
                    message.getM_status(), message.getM_Time(), message.getM_MessagesTypeID(), message.getM_ToUserID(), message.getM_FromUserID()
                    , 0, key);
            messageList.setFriend_Name(name);
            messageList.setFriend_img_face(img);
            listDao.update(messageList);
        }


    }


    @Override
    public void sendMessage(Message message, FutureListener listener) {
        MessageHolder messageHolder = new MessageHolder();
        messageHolder.setBody(Serializer.serialize(message));
        messageHolder.setSign(ProtocolHeader.REQUEST);
        messageHolder.setType(ProtocolHeader.PERSON_MESSAGE);
        NettyClient.getInstance().sendMsgToServer(messageHolder, listener);
        AppLog.e(messageHolder.toString());
    }
}
