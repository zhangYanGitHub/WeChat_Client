package com.zhang.chat.bean;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import com.zhang.chat.utils.CharUtils;

/**
 * @Author: ZhangYan
 * @Description:
 * @Date Create In: 2017/9/24 22:44
 * @Modified By:
 */

@Entity
public class Friend implements Comparable<Friend>, Serializable {
    static final long serialVersionUID = 536871009;
    @Id(autoincrement = true)
    private long f_id;
    private long user_id;
    private String user_name;
    private int user_sex = -1;
    private String user_desc;
    private String user_phone;
    private String user_account;
    private String user_img_face_path;

    /**
     * 备注昵称    Null
     */
    private String f_friend_type_id;
    /**
     * (所属分组ID)    外键
     */
    private int f_friend_groups_id;


    /**
     * 是否 验证通过
     */
    private boolean friend_state = true;

    public Friend(long user_id, String user_name, int user_sex, String user_desc, String user_phone,
                  String user_img_face_path, String f_friend_type_id, int f_friend_groups_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_desc = user_desc;
        this.user_phone = user_phone;
        this.user_img_face_path = user_img_face_path;
        this.f_friend_type_id = f_friend_type_id;
        this.f_friend_groups_id = f_friend_groups_id;
    }

    public Friend(User user) {
        this.user_id = user.getM_Id();
        this.user_name = user.getUser_name();
        this.user_sex = user.getUser_sex();
        this.user_desc = user.getUser_desc();
        this.user_phone = user.getUser_phone();
        this.user_img_face_path = user.getUser_img_face_path();
        this.f_friend_type_id = "";
        this.f_friend_groups_id = 1;
    }

    @Generated(hash = 583589384)
    public Friend(long f_id, long user_id, String user_name, int user_sex, String user_desc,
            String user_phone, String user_account, String user_img_face_path, String f_friend_type_id,
            int f_friend_groups_id, boolean friend_state) {
        this.f_id = f_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_desc = user_desc;
        this.user_phone = user_phone;
        this.user_account = user_account;
        this.user_img_face_path = user_img_face_path;
        this.f_friend_type_id = f_friend_type_id;
        this.f_friend_groups_id = f_friend_groups_id;
        this.friend_state = friend_state;
    }

    @Generated(hash = 287143722)
    public Friend() {
    }

    public long getF_id() {
        return f_id;
    }

    public void setF_id(long f_id) {
        this.f_id = f_id;
    }




    @Override
    public String toString() {
        return "Friend{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_sex=" + user_sex +
                ", user_desc='" + user_desc + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_account='" + user_account + '\'' +
                ", user_img_face_path='" + user_img_face_path + '\'' +
                ", f_friend_type_id='" + f_friend_type_id + '\'' +
                ", f_friend_groups_id=" + f_friend_groups_id +
                '}';
    }


    public char getHeadLetter() {
        char headLetter = CharUtils.getHeadChar(user_name);
        return headLetter;
    }


    @Override
    public int compareTo(@NonNull Friend object) {

        Friend that = (Friend) object;
        if (getHeadLetter() == '#') {
            if (that.getHeadLetter() == '#') {
                return 0;
            }
            return 1;
        }
        if (that.getHeadLetter() == '#') {
            return -1;
        } else if (that.getHeadLetter() > getHeadLetter()) {
            return -1;
        } else if (that.getHeadLetter() == getHeadLetter()) {
            return 0;
        }
        return 1;
    }

    public void setFriend_state(boolean friend_state) {
        this.friend_state = friend_state;
    }

    public boolean isFriend_state() {
        return friend_state;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_img_face_path() {
        return user_img_face_path;
    }

    public void setUser_img_face_path(String user_img_face_path) {
        this.user_img_face_path = user_img_face_path;
    }

    public String getF_friend_type_id() {
        return f_friend_type_id;
    }

    public void setF_friend_type_id(String f_friend_type_id) {
        this.f_friend_type_id = f_friend_type_id;
    }

    public int getF_friend_groups_id() {
        return f_friend_groups_id;
    }

    public void setF_friend_groups_id(int f_friend_groups_id) {
        this.f_friend_groups_id = f_friend_groups_id;
    }

    public boolean getFriend_state() {
        return this.friend_state;
    }
}
