package com.zhang.chat.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

/**
 * Created by 张俨 on 2017/9/27.
 */
@Entity
public class MessageList {


    /**
     * (消息ID)    主键，自增
     */
    @Id(autoincrement = true)
    private Long M_ID;
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

    @Transient
    private String Friend_img_face;
    @Transient
    private String Friend_Name;

    private int newNumber;
    private String keyFlag;


    public Long getFriendID() {
        if (M_ToUserID == Long.parseLong(ShareUtil.getPreferStr(Constant.USER_NAME))) {
            return M_FromUserID;

        } else {
            return M_ToUserID;
        }
    }

    public Long getM_ID() {
        return this.M_ID;
    }

    public void setM_ID(Long M_ID) {
        this.M_ID = M_ID;
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

    public int getNewNumber() {
        return this.newNumber;
    }

    public void setNewNumber(int newNumber) {
        this.newNumber = newNumber;
    }

    public String getKeyFlag() {
        return this.keyFlag;
    }

    public void setKeyFlag(String keyFlag) {
        this.keyFlag = keyFlag;
    }


    public MessageList(Message message, int newNumber) {
        M_ID = message.getM_ID();
        this.M_Time = message.getM_Time();
        this.M_PostMessages = message.getM_PostMessages();
        this.M_FromUserID = message.getM_FromUserID();
        this.M_ToUserID = message.getM_ToUserID();
        this.M_MessagesTypeID = message.getM_MessagesTypeID();
        this.newNumber = newNumber;

        this.keyFlag = String.valueOf(M_FromUserID + "" + M_ToUserID);
    }

    @Generated(hash = 1224774890)
    public MessageList(Long M_ID, String M_PostMessages, int M_status, String M_Time,
                       int M_MessagesTypeID, long M_ToUserID, long M_FromUserID, int newNumber,
                       String keyFlag) {
        this.M_ID = M_ID;
        this.M_PostMessages = M_PostMessages;
        this.M_status = M_status;
        this.M_Time = M_Time;
        this.M_MessagesTypeID = M_MessagesTypeID;
        this.M_ToUserID = M_ToUserID;
        this.M_FromUserID = M_FromUserID;
        this.newNumber = newNumber;
        this.keyFlag = keyFlag;
    }

    @Generated(hash = 1974901781)
    public MessageList() {
    }


    public void setFriend_Name(String friend_Name) {
        Friend_Name = friend_Name;
    }

    public String getFriend_Name() {
        return Friend_Name;
    }

    public void setFriend_img_face(String friend_img_face) {
        Friend_img_face = friend_img_face;
    }

    public String getFriend_img_face() {
        return Friend_img_face;
    }

    public String getKey() {
        if (M_ToUserID > M_FromUserID){
            return String.valueOf(getM_FromUserID() + "" + this.getM_ToUserID());
        }else {
            return String.valueOf(getM_ToUserID() + "" + this.getM_FromUserID());
        }
    }
}
