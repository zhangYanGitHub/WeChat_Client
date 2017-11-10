package com.zhang.chat.main;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.VerificationDao;

import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class MainModel extends MainContract.MainModel {
    private FriendDao friendDao;
    private MessageDao messageDao;
    private  MessageListDao listDao;
    private  VerificationDao verificationDao;

    public MainModel() {
        if(getSession() != null){
            friendDao = getSession().getFriendDao();
            messageDao = getSession().getMessageDao();
            listDao = getSession().getMessageListDao();
            verificationDao = getSession().getVerificationDao();
        }
    }


    @Override
    public void add(Message message) {
        List<Message> list = messageDao.queryBuilder().where(MessageDao.Properties.Flag.eq(1)).list();

        long insert = messageDao.insertOrReplace(message);
    }

    @Override
    public void addBySocket(Message message) {
        message.setM_ID(null);
        message.setIsRead(false);
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
        if (ListUtil.isEmpty(list)) {

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
    public void add(ListMessage messages) {
        if (messages == null || ListUtil.isEmpty(messages.getList())) return;
        for (ListMessage.Message message : messages.getList()) {
            addBySocket(new Message(message));
        }
    }

    @Override
    public void addBySocket(ListMessage messages) {
        if (messages == null || ListUtil.isEmpty(messages.getList())) return;
        for (ListMessage.Message message : messages.getList()) {
            Message message1 = new Message(message);
            addBySocket(message1);
        }
    }

    @Override
    public void addBySocket(Verification deserialize) {
        deserialize.setIsRead(false);
        List<Verification> list = verificationDao.queryBuilder().whereOr(VerificationDao.Properties.Friend_user_id.eq(deserialize.getFriend_user_id()),
                VerificationDao.Properties.User_friend_id.eq(deserialize.getUser_friend_id())).list();
        if (ListUtil.isEmpty(list)) {
            verificationDao.insert(deserialize);
        } else {
            deserialize.setM_id(list.get(0).getM_id());
            verificationDao.update(deserialize);
        }

        AppLog.e(TAG + deserialize.toString());
    }

    @Override
    public void addBySocket(Friend deserialize) {
        friendDao.insertOrReplace(deserialize);
    }

    @Override
    public int[] getNotice() {
        int[] notices = new int[4];
        List<MessageList> list = listDao.queryBuilder()
                .where(MessageListDao.Properties.NewNumber.gt(0)).list();
        List<Verification> list1 = verificationDao.queryBuilder()
                .where(VerificationDao.Properties.IsRead.eq(false)).list();
        for (MessageList messageList : list) {
            notices[0] += messageList.getNewNumber();
        }
        if(ListUtil.isNotEmpty(list1)){
            notices[1] = list1.size();
        }
        return notices;
    }


}
