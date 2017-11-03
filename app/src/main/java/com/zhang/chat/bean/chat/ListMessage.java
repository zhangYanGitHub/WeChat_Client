package com.zhang.chat.bean.chat;

import java.util.List;

/**
 * Create by ZhangYan on 2017/10/14.
 */

public class ListMessage  {

    private List<Message> list;

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    public static class Message{

        /**
         * m_FromUserID : 10002
         * m_ID : 29
         * m_MessagesTypeID : 1
         * m_PostMessages : _额-有色差
         * m_Time : 1508330283925
         * m_ToUserID : 10001
         * m_status : 1
         */

        private long m_FromUserID;
        private long m_ID;
        private int m_MessagesTypeID;
        private String m_PostMessages;
        private String m_Time;
        private int m_ToUserID;
        private int m_status;

        public long getM_FromUserID() {
            return m_FromUserID;
        }

        public void setM_FromUserID(long m_FromUserID) {
            this.m_FromUserID = m_FromUserID;
        }

        public long getM_ID() {
            return m_ID;
        }

        public void setM_ID(long m_ID) {
            this.m_ID = m_ID;
        }

        public int getM_MessagesTypeID() {
            return m_MessagesTypeID;
        }

        public void setM_MessagesTypeID(int m_MessagesTypeID) {
            this.m_MessagesTypeID = m_MessagesTypeID;
        }

        public String getM_PostMessages() {
            return m_PostMessages;
        }

        public void setM_PostMessages(String m_PostMessages) {
            this.m_PostMessages = m_PostMessages;
        }

        public String getM_Time() {
            return m_Time;
        }

        public void setM_Time(String m_Time) {
            this.m_Time = m_Time;
        }

        public int getM_ToUserID() {
            return m_ToUserID;
        }

        public void setM_ToUserID(int m_ToUserID) {
            this.m_ToUserID = m_ToUserID;
        }

        public int getM_status() {
            return m_status;
        }

        public void setM_status(int m_status) {
            this.m_status = m_status;
        }
    }
}
