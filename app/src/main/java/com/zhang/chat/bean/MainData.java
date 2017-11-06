package com.zhang.chat.bean;


import com.zhang.chat.bean.chat.Message;
import com.zhang.chat.bean.chat.Verification;

import java.util.List;

public class MainData {
    private List<Friend> friends;
    private List<Message> messageList;
    private List<Verification> verifications;

    private List<MessageList> latestMessage;

    private User user;

    public List<MessageList> getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(List<MessageList> latestMessage) {
        this.latestMessage = latestMessage;
    }

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

    public static class MessageList {
        private Message message;
        private int number;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
