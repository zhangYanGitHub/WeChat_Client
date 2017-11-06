package com.zhang.chat.splash;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import java.util.List;

import com.greendao.gen.VerificationDao;
import com.zhang.chat.app.App;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.bean.chat.Verification;
import com.zhang.chat.net.ApiFunction;
import com.zhang.chat.net.RetrofitProvider;
import com.zhang.chat.net.RxSchedulers;
import com.zhang.chat.utils.ListUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class SplashModel extends SplashContract.SplashModel {

    private UserDao userDao;
    private  MessageListDao messageListDao;
    private  VerificationDao verificationDao;
    private  FriendDao friendDao;
    private  MessageDao messageDao;

    public SplashModel() {
        userDao = getUserDao();
        if(getSession() != null){
            messageListDao = getSession().getMessageListDao();
            friendDao = getSession().getFriendDao();
            verificationDao = getSession().getVerificationDao();
            messageDao = getSession().getMessageDao();
        }
    }

    @Override
    public User getUserFromSQL() {
        List<User> list = userDao
                .queryBuilder()
                .where(UserDao.Properties.U_UserState.eq(1))
                .list();
        if (ListUtil.isEmpty(list)) return null;
        User user = list.get(0);
        return user;
    }

    @Override
    public Observable<MainData> getUserData(long m_id) {
        return RetrofitProvider.getService().getUserData(String.valueOf(m_id)).flatMap(new ApiFunction<MainData>()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    @Override
    public void save(MainData mainData) {
        List<Friend> friends = mainData.getFriends();
        List<MainData.MessageList> latestMessage = mainData.getLatestMessage();
        User user = mainData.getUser();
        List<Verification> verifications = mainData.getVerifications();
        List<Message> messageList2 = mainData.getMessageList();

        userDao.save(user);
        for (Friend friend : friends) {
            friendDao.save(friend);
        }

        for (Verification verification : verifications) {
            verificationDao.save(verification);
        }
        for (Message message : messageList2) {
            message.setIsRead(false);
            messageDao.save(message);
        }

        for (MainData.MessageList messageList : latestMessage) {
            messageListDao.deleteAll();
            MessageList messageList1 = new MessageList(messageList.getMessage(), messageList.getNumber());
            messageListDao.save(messageList1);
        }
    }


}
