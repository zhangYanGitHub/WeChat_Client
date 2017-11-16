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
    private String M_PostMessages;
    /**
     * 1  发送中
     * 2  发送成功
     * 3  发送失败
     * 4  未读
     */
    private int M_status;
    /**
     * (发送时间)    默认值
     */
    private String M_Time;
    /**
     * (消息类型ID)    外键
     * 1 文本信息
     * 2 图片信息
     * 3 视频信息
     * 4 文件
     */
    private int M_MessagesTypeID;
    /**
     * (发送者ID)指向用户表    外键
     */
    private long M_ToUserID;
    /**
     * (接收者ID)指向用户表    外键
     */
    private long M_FromUserID;
    private long flag = 1;

    private boolean isNew;

    private boolean isRead = true;



    public Message(Long m_ID, String m_PostMessages, int m_status, String m_Time, int m_MessagesTypeID, long m_ToUserID, long m_FromUserID) {
        this.m_ID = m_ID;
        M_PostMessages = m_PostMessages;
        M_status = m_status;
        M_Time = m_Time;
        M_MessagesTypeID = m_MessagesTypeID;
        M_ToUserID = m_ToUserID;
        M_FromUserID = m_FromUserID;
    }


    public Message(ListMessage.Message message) {
        m_ID = message.getM_ID();
        M_PostMessages = message.getM_PostMessages();
        M_status = message.getM_status();
        M_Time = message.getM_Time();
        M_MessagesTypeID = message.getM_MessagesTypeID();
        M_ToUserID = message.getM_ToUserID();
        M_FromUserID = message.getM_FromUserID();
    }


    @Generated(hash = 265413287)
    public Message(Long m_ID, String M_PostMessages, int M_status, String M_Time, int M_MessagesTypeID, long M_ToUserID, long M_FromUserID,
            long flag, boolean isNew, boolean isRead) {
        this.m_ID = m_ID;
        this.M_PostMessages = M_PostMessages;
        this.M_status = M_status;
        this.M_Time = M_Time;
        this.M_MessagesTypeID = M_MessagesTypeID;
        this.M_ToUserID = M_ToUserID;
        this.M_FromUserID = M_FromUserID;
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

    public void setM_ID(Long M_ID) {
        this.m_ID = M_ID;
    }

    public String getM_PostMessages() {
        return this.M_PostMessages;
    }

    public void setM_PostMessages(String M_PostMessages) {
        this.M_PostMessages = M_PostMessages;
    }

    public int getM_status() {
        return this.M_status;
    }

    public void setM_status(int M_status) {
        this.M_status = M_status;
    }

    public String getM_Time() {
        return this.M_Time;
    }

    public void setM_Time(String M_Time) {
        this.M_Time = M_Time;
    }

    public int getM_MessagesTypeID() {
        return this.M_MessagesTypeID;
    }

    public void setM_MessagesTypeID(int M_MessagesTypeID) {
        this.M_MessagesTypeID = M_MessagesTypeID;
    }

    public long getM_ToUserID() {
        return this.M_ToUserID;
    }

    public void setM_ToUserID(long M_ToUserID) {
        this.M_ToUserID = M_ToUserID;
    }

    public long getM_FromUserID() {
        return this.M_FromUserID;
    }

    public void setM_FromUserID(long M_FromUserID) {
        this.M_FromUserID = M_FromUserID;
    }


    @Override
    public String toString() {
        return "Message{" +
                "M_ID=" + m_ID +
                ", M_PostMessages='" + M_PostMessages + '\'' +
                ", M_status=" + M_status +
                ", M_Time='" + M_Time + '\'' +
                ", M_MessagesTypeID=" + M_MessagesTypeID +
                ", M_ToUserID=" + M_ToUserID +
                ", M_FromUserID=" + M_FromUserID +
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

        if (M_ToUserID > M_FromUserID) {
            return String.valueOf(getM_FromUserID() + "" + this.getM_ToUserID());
        } else {
            return String.valueOf(getM_ToUserID() + "" + this.getM_FromUserID());
        }
    }
}
