package com.zhang.chat.main.chat.presenter;

import java.util.Date;
import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.chat.contract.ChatContract;
import com.zhang.chat.netty.FutureListener;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class ChatPresenter extends ChatContract.Presenter {


    public ChatPresenter() {

    }

    @Override
    public void onStart() {


    }

    @Override
    public void sendMessage(String holder) {

        Friend friend = mView.getFriend();
        Message message = new Message();
        message.setM_ID(null);
        message.setM_FromUserID(friend.getUser_id());
        message.setM_ToUserID(Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME)));
        message.setM_Time(String.valueOf(new Date().getTime()));
        message.setM_status(2);
        message.setM_PostMessages(holder);
        message.setM_MessagesTypeID(1);
        AppLog.e(message.toString());

        mModel.sendMessage(message, new FutureListener() {
            @Override
            public void success() {
                message.setM_status(2);
                mModel.add(message);
            }

            @Override
            public void error() {
                message.setM_status(3);

            }
        });
        mView.notify(message);
    }

    @Override
    public void getMessage() {
        Friend friend = mView.getFriend();
        List<Message> message = mModel.getMessage(friend.getUser_id());
        mView.notify(message);
    }

    @Override
    public void getResponse() {
        Message message = mModel.Message(mView.getFriend().getUser_id());
        mView.notify(message);
    }

    @Override
    public User getUser() {
        return mModel.getUser();
    }
}
