package com.zhang.chat.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

/**
 * Created by 张俨 on 2017/9/27.
 */
@Entity
public class Message {


    /**
     * (消息ID)    主键，自增
     */
    @Id(autoincrement = true)
    private Long m_ID;
    /**
     * 消息内容
     */
    private String m_PostMessages;
    /**
     * 1  发送中
     * 2  发送成功
     * 3  发送失败
     * 4  未读
     */
    private int m_status;
    /**
     * (发送时间)    默认值
     */
    private String m_Time;
    /**
     * (消息类型ID)    外键
     * 1 文本信息
     * 2 图片信息
     * 3 视频信息
     * 4 文件
     */
    private int m_MessagesTypeID;
    /**
     * (发送者ID)指向用户表    外键
     */
    private long m_ToUserID;
    /**
     * (接收者ID)指向用户表    外键
     */
    private long m_FromUserID;
    private long flag = 1;

    private boolean isNew;

    private boolean isRead = true;



    public Message(Long m_ID, String m_PostMessages, int m_status, String m_Time, int m_MessagesTypeID, long m_ToUserID, long m_FromUserID) {
        this.m_ID = m_ID;
        this.m_PostMessages = m_PostMessages;
        this.m_status = m_status;
        this.m_Time = m_Time;
        this.m_MessagesTypeID = m_MessagesTypeID;
        this.m_ToUserID = m_ToUserID;
        this.m_FromUserID = m_FromUserID;
    }


    public Message(ListMessage.Message message) {
        m_ID = message.getM_ID();
        m_PostMessages = message.getM_PostMessages();
        m_status = message.getM_status();
        m_Time = message.getM_Time();
        m_MessagesTypeID = message.getM_MessagesTypeID();
        m_ToUserID = message.getM_ToUserID();
        m_FromUserID = message.getM_FromUserID();
    }


    @Generated(hash = 486226015)
    public Message(Long m_ID, String m_PostMessages, int m_status, String m_Time, int m_MessagesTypeID, long m_ToUserID, long m_FromUserID,
            long flag, boolean isNew, boolean isRead) {
        this.m_ID = m_ID;
        this.m_PostMessages = m_PostMessages;
        this.m_status = m_status;
        this.m_Time = m_Time;
        this.m_MessagesTypeID = m_MessagesTypeID;
        this.m_ToUserID = m_ToUserID;
        this.m_FromUserID = m_FromUserID;
        this.flag = flag;
        this.isNew = isNew;
        this.isRead = isRead;
    }


    @Generated(hash = 637306882)
    public Message() {
    }



    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }


    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isNew() {
        return isNew;
    }

    public Long getM_ID() {
        return this.m_ID;
    }

    public void setM_ID(Long m_ID) {
        this.m_ID = m_ID;
    }

    public String getM_PostMessages() {
        return this.m_PostMessages;
    }

    public void setM_PostMessages(String m_PostMessages) {
        this.m_PostMessages = m_PostMessages;
    }

    public int getM_status() {
        return this.m_status;
    }

    public void setM_status(int m_status) {
        this.m_status = m_status;
    }

    public String getM_Time() {
        return this.m_Time;
    }

    public void setM_Time(String m_Time) {
        this.m_Time = m_Time;
    }

    public int getM_MessagesTypeID() {
        return this.m_MessagesTypeID;
    }

    public void setM_MessagesTypeID(int m_MessagesTypeID) {
        this.m_MessagesTypeID = m_MessagesTypeID;
    }

    public long getM_ToUserID() {
        return this.m_ToUserID;
    }

    public void setM_ToUserID(long m_ToUserID) {
        this.m_ToUserID = m_ToUserID;
    }

    public long getm_FromUserID() {
        return this.m_FromUserID;
    }

    public void setM_FromUserID(long m_FromUserID) {
        this.m_FromUserID = m_FromUserID;
    }


    @Override
    public String toString() {
        return "Message{" +
                "m_ID=" + m_ID +
                ", m_PostMessages='" + m_PostMessages + '\'' +
                ", m_status=" + m_status +
                ", m_Time='" + m_Time + '\'' +
                ", m_MessagesTypeID=" + m_MessagesTypeID +
                ", m_ToUserID=" + m_ToUserID +
                ", m_FromUserID=" + m_FromUserID +
                ", flag=" + flag +
                ", isNew=" + isNew +
                '}';
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public long getFlag() {
        return this.flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public String getKey() {

        if (m_ToUserID > m_FromUserID) {
            return String.valueOf(getm_FromUserID() + "" + this.getM_ToUserID());
        } else {
            return String.valueOf(getM_ToUserID() + "" + this.getm_FromUserID());
        }
    }


    public long getM_FromUserID() {
        return this.m_FromUserID;
    }
}
