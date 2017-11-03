package com.zhang.chat.main.chat.model;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.main.chat.contract.ChatFragmentContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.ShareUtil;
import io.reactivex.Observable;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class ChatFragmentModel extends ChatFragmentContract.Model {

    private final FriendDao friendDao;
    private final UserDao userDao;
    private final MessageDao messageDao;
    private final MessageListDao listDao;
    private Map<String, List<Message>> map;

    public ChatFragmentModel() {
        friendDao = GreenDaoManager.getInstance().getUserDaoSession().getFriendDao();
        userDao = getUserDao();
        messageDao = getSession().getMessageDao();
        listDao = getSession().getMessageListDao();
    }

    public void start() {
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        long user_id = Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME));
        QueryBuilder<Message> qb = messageDao.queryBuilder();
        List<Message> list = qb.where(
                qb.or(MessageDao.Properties.M_ToUserID.eq(user_id)
                        , MessageDao.Properties.M_FromUserID.eq(user_id))).list();

        for (Message message : list) {
            String l = message.getKey();
            boolean b = map.containsKey(l);
            if (!b) {
                ArrayList<Message> messages = new ArrayList<>();
                map.put(l, messages);
            } else {
                map.get(l).add(message);
            }
        }
        for (List<Message> data : map.values()) {
            data.sort((o1, o2) -> {
                return (int) (Long.parseLong(o1.getM_Time()) - Long.parseLong(o2.getM_Time()));
            });
            int num = 0;
            if (ListUtil.isEmpty(data)) continue;
            Message message = data.get(0);
            for (Message message1 : data) {
                boolean isRead = message1.getIsRead();
                if (!isRead) {
                    num++;
                }
            }
            long friend_id;
            if (message.getM_FromUserID() == Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME))) {
                friend_id = message.getM_ToUserID();
            } else {
                friend_id = message.getM_FromUserID();
            }
            List<Friend> list2 = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(friend_id)).list();
            String img = null;
            String name = null;
            if (ListUtil.isEmpty(list2)) {

            } else {
                Friend friend = list2.get(0);
                img = friend.getUser_img_face_path();
                name = friend.getUser_name();
            }
            String l = message.getKey();
            MessageList messageList = new MessageList(message, num);
            List<MessageList> list1 = listDao.queryBuilder().where(MessageListDao.Properties.KeyFlag.eq(l)).list();
            if (ListUtil.isEmpty(list1)) {
                messageList.setFriend_img_face(img);
                messageList.setFriend_Name(name);
                listDao.insertOrReplace(messageList);
            } else {
                messageList.setM_ID(list1.get(0).getM_ID());
                messageList.setFriend_img_face(img);
                messageList.setFriend_Name(name);
                listDao.update(messageList);
            }
        }
    }

    @Override
    public List<MessageList> getLatestMessage() {
        List<MessageList> list = listDao.queryBuilder().list();
        return list;
    }

    @Override
    public Friend getFriend(Long f_id) {
        List<Friend> list = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(f_id)).list();
        if (ListUtil.isEmpty(list)) return null;
        return list.get(0);
    }

    @Override
    public Observable<ListMessage> getMessageList() {
        return RetrofitProvider
                .getService()
                .getMessageList()
                .flatMap(new ApiFunction<ListMessage>())
                .compose(RxSchedulers.<ListMessage>io_main());

    }

    @Override
    public void add(Message message) {
        if (message.getM_FromUserID() == 0) {
            AppLog.e(TAG + message.toString());
            return;
        }
        message.setM_ID(null);
        long insert = messageDao.insert(message);
        String key = message.getKey();
        boolean b = map.containsKey(key);
        message.setIsRead(false);
        long friend_id;
        if (message.getM_FromUserID() == Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME))) {
            friend_id = message.getM_ToUserID();
        } else {
            friend_id = message.getM_FromUserID();
        }
        List<Friend> list = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(friend_id)).list();
        String img = null;
        String name = null;
        if (ListUtil.isEmpty(list)) {

        } else {
            Friend friend = list.get(0);
            img = friend.getUser_img_face_path();
            name = friend.getUser_name();
        }
        if (b) {
            map.get(key).add(message);
            MessageList messageList = new MessageList(message, map.get(key).size());
            messageList.setFriend_Name(name);
            messageList.setFriend_img_face(img);
            listDao.update(messageList);
        } else {
            ArrayList<Message> messages = new ArrayList<>();
            map.put(key, messages);
            messages.add(message);
            MessageList messageList = new MessageList(message, map.get(key).size());
            messageList.setFriend_Name(name);
            messageList.setFriend_img_face(img);
            listDao.insert(messageList);
        }

    }

    @Override
    public void update(MessageList stringLocalChat) {
        String key = stringLocalChat.getKey();

        stringLocalChat.setNewNumber(0);
        listDao.update(stringLocalChat);
        boolean b = map.containsKey(key);
        if (!b) {
            return;
        }
        for (Message message : map.get(key)) {
            message.setIsRead(true);
            messageDao.update(message);
        }

    }

    @Override
    public void add(ListMessage messages) {
        if (messages == null || ListUtil.isEmpty(messages.getList())) return;
        for (ListMessage.Message message : messages.getList()) {
            Message message1 = new Message(message);
            add(message1);
        }

    }
}
