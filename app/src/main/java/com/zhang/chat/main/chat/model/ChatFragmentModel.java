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
    public void update(MessageList stringLocalChat) {
        String key = stringLocalChat.getKey();

        stringLocalChat.setNewNumber(0);
        listDao.update(stringLocalChat);

    }


}
