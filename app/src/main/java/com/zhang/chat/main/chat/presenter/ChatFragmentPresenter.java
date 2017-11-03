package com.zhang.chat.main.chat.presenter;

import java.util.List;

import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.MessageList;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.main.chat.contract.ChatFragmentContract;
import com.zhang.chat.net.ApiSubscribe;
import com.zhang.chat.utils.AppToast;
import com.zhang.chat.utils.ListUtil;

/**
 * Create by ZhangYan on 2017/9/26.
 */

public class ChatFragmentPresenter extends ChatFragmentContract.Presenter {


    private List<MessageList> messages;

    public ChatFragmentPresenter() {

    }

    @Override
    public void onStart() {
        mModel.start();
    }

    @Override
    public void setList(List<MessageList> messages) {
        this.messages = messages;
        getMessageList();
    }

    @Override
    public void refresh() {
        List<MessageList> latestMessage = mModel.getLatestMessage();
        messages.clear();
        messages.addAll(latestMessage);
        mView.notifyList();
    }

    @Override
    public Friend getFriend(Long f_id) {
        return mModel.getFriend(f_id);
    }

    @Override
    public void getMessageList() {
        List<MessageList> latestMessage = mModel.getLatestMessage();
        messages.addAll(latestMessage);
        mView.notifyList();
    }

    @Override
    public void update(MessageList stringLocalChat) {
        mModel.update(stringLocalChat);
    }
}
