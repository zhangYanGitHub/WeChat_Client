package com.zhang.chat.bean;


import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.Verification;

import java.util.List;

public class MainData {
    private List<Friend> friends;
    private List<Message> messageList;
    private List<Verification> verifications;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<Verification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<Verification> verifications) {
        this.verifications = verifications;
    }
}
