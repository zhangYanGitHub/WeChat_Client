package com.zhang.chat.main.chat.model;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import java.util.List;
import java.util.Map;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.db.GreenDaoManager;
import com.zhang.chat.main.chat.contract.ChatFragmentContract;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.StrUtil;

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

    }

    @Override
    public List<MessageList> getLatestMessage() {
        List<MessageList> list = listDao.queryBuilder().list();
        for (MessageList messageList : list) {
            final String friend_img_face = messageList.getFriend_img_face();
            final String friend_name = messageList.getFriend_Name();

            if (StrUtil.isBlank(friend_img_face) || StrUtil.isBlank(friend_name)) {
                final Long friendID = messageList.getFriendID();
                final List<Friend> list1 = friendDao.queryBuilder().where(FriendDao.Properties.User_id.eq(friendID)).list();
                if (ListUtil.isNotEmpty(list1)) {
                    final Friend friend = list1.get(0);
                    messageList.setFriend_img_face(friend.getUser_img_face_path());
                    messageList.setFriend_Name(friend.getF_friend_type_id());
                }
            }
        }
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
    public void update(MessageList stringLocalChat) {
        String key = stringLocalChat.getKey();

        stringLocalChat.setNewNumber(0);
        listDao.update(stringLocalChat);

    }


}
