package com.zhang.chat.bean.chat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author: ZhangYan
 * @Description:
 * @Date Create In: 2017/10/17 19:06
 * @Modified By:
 */
@Entity
public class Verification {

    /**
     * 主键
     */
    @Id(autoincrement = true)
    private long m_id;
    /**
     * 添加发起人id
     */
    private long user_friend_id;
    /**
     * 添加接收人id
     */
    private long friend_user_id;

    /**
     * 1 发起添加朋友请求
     * 2 通过朋友验证
     * 3 拒绝添加
     */
    private int m_state;
    private String message;
    /**
     * 是否知道
     */
    private boolean isRead;

    private String friend_name;

    private String friend_img_face;

    @Generated(hash = 416655713)
    public Verification(long m_id, long user_friend_id, long friend_user_id,
                        int m_state, String message, boolean isRead, String friend_name,
                        String friend_img_face) {
        this.m_id = m_id;
        this.user_friend_id = user_friend_id;
        this.friend_user_id = friend_user_id;
        this.m_state = m_state;
        this.message = message;
        this.isRead = isRead;
        this.friend_name = friend_name;
        this.friend_img_face = friend_img_face;
    }

    @Generated(hash = 1358926583)
    public Verification() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setFriend_img_face(String friend_img_face) {
        this.friend_img_face = friend_img_face;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_img_face() {
        return friend_img_face;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public long getM_id() {
        return this.m_id;
    }

    public void setM_id(long m_id) {
        this.m_id = m_id;
    }

    public long getUser_friend_id() {
        return this.user_friend_id;
    }

    public void setUser_friend_id(long user_friend_id) {
        this.user_friend_id = user_friend_id;
    }

    public long getFriend_user_id() {
        return this.friend_user_id;
    }

    public void setFriend_user_id(long friend_user_id) {
        this.friend_user_id = friend_user_id;
    }

    public int getM_state() {
        return this.m_state;
    }

    public void setM_state(int m_state) {
        this.m_state = m_state;
    }

    @Override
    public String toString() {
        return "Verification{" +
                "m_id=" + m_id +
                ", user_friend_id=" + user_friend_id +
                ", friend_user_id=" + friend_user_id +
                ", m_state=" + m_state +
                '}';
    }
}
