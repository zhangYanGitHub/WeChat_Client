package com.zhang.chat.splash;

import com.greendao.gen.FriendDao;
import com.greendao.gen.MessageDao;
import com.greendao.gen.MessageListDao;
import com.greendao.gen.UserDao;

import java.util.List;

import com.greendao.gen.VerificationDao;
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

/**
 * Created by 张俨 on 2017/9/8.
 */

public class SplashModel extends SplashContract.SplashModel {

    private UserDao userDao;
    private MessageListDao messageListDao;
    private VerificationDao verificationDao;
    private FriendDao friendDao;
    private MessageDao messageDao;

    public SplashModel() {
        userDao = getUserDao();
        if (getSession() != null) {
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
        return RetrofitProvider.getService().getUserData().flatMap(new ApiFunction<MainData>()).compose(RxSchedulers.io_main());
    }

    @Override
    public void save(MainData mainData) {
        List<Friend> friends = mainData.getFriends();
        List<MainData.MessageList> latestMessage = mainData.getLatestMessage();
        User user = mainData.getUser();
        List<Verification> verifications = mainData.getVerifications();
        List<Message> messageList2 = mainData.getMessageList();

        if (ListUtil.isNotEmpty(friends)) {
            for (Friend friend : friends) {
                friendDao.insertOrReplace(friend);
            }
        }
        user.setU_UserState(1);
        userDao.insertOrReplace(user);

        if (ListUtil.isNotEmpty(verifications)) {
            for (Verification verification : verifications) {
                final QueryBuilder<Verification> builder = verificationDao.queryBuilder();
                final List<Verification> list = builder.whereOr(builder.and(VerificationDao.Properties.Friend_user_id.eq(verification.getUser_friend_id())
                        , VerificationDao.Properties.User_friend_id.eq(verification.getFriend_user_id()))
                        , builder.and(VerificationDao.Properties.Friend_user_id.eq(verification.getFriend_user_id())
                                , VerificationDao.Properties.User_friend_id.eq(verification.getUser_friend_id())
                        )).list();
                if (ListUtil.isNotEmpty(list)) {
                    final Verification verification1 = list.get(0);
                    verification.setM_id(verification1.getM_id());
                    verification.setIsRead(verification1.getIsRead());
                }
                verificationDao.insertOrReplace(verification);
            }
        }

        if (ListUtil.isNotEmpty(latestMessage)) {

            for (MainData.MessageList messageList : latestMessage) {
                MessageList messageList1 = new MessageList(messageList.getMessage(), messageList.getNumber());
                final List<MessageList> list = messageListDao.queryBuilder().where(MessageListDao.Properties.KeyFlag.eq(messageList1.getKey())).list();
                if (ListUtil.isNotEmpty(list)) {
                    messageList1.setM_ID(list.get(0).getM_ID());
                }
                messageListDao.insertOrReplace(messageList1);
            }
        }
        if (ListUtil.isNotEmpty(messageList2)) {
            for (Message message : messageList2) {
                message.setIsRead(false);
                messageDao.insertOrReplace(message);
            }
        }
    }


}
