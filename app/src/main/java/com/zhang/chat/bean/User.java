package com.zhang.chat.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by 张俨 on 2017/9/7.
 */

@Entity
public class User implements Serializable {
    static final long serialVersionUID = 536871008;

    @Id(autoincrement = true)
    private long m_id;
    private long uu_id;
    private String user_name;
    private int user_sex = -1;
    private String user_real_name;
    private String user_password;
    private String user_desc;
    private String user_phone;
    private String user_account;
    private String user_email;
    /**
     * 头像路径
     */
    private String user_img_face_path;
    /**
     * 用户注册日期
     */
    private long user_register_date;
    /**
     * (国家ID)    外键
     */
    private String u_NationID;

    /**
     * （省份ID）    外键
     */
    private String u_Province;
    /**
     * （城市ID）    外键
     */
    private String u_City;
    private String address_message;
    /**
     * 好友策略ID    外键
     */
    private int U_FriendshipPolicy;
    /**
     * (用户状态ID)    外键
     */
    private int U_UserState;

    public User(String name, String phone) {
        user_phone = phone;
        user_name = name;
    }




    @Generated(hash = 586692638)
    public User() {
    }




    @Generated(hash = 1945182954)
    public User(long m_id, long uu_id, String user_name, int user_sex,
            String user_real_name, String user_password, String user_desc,
            String user_phone, String user_account, String user_email,
            String user_img_face_path, long user_register_date, String u_NationID,
            String u_Province, String u_City, String address_message,
            int U_FriendshipPolicy, int U_UserState) {
        this.m_id = m_id;
        this.uu_id = uu_id;
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_real_name = user_real_name;
        this.user_password = user_password;
        this.user_desc = user_desc;
        this.user_phone = user_phone;
        this.user_account = user_account;
        this.user_email = user_email;
        this.user_img_face_path = user_img_face_path;
        this.user_register_date = user_register_date;
        this.u_NationID = u_NationID;
        this.u_Province = u_Province;
        this.u_City = u_City;
        this.address_message = address_message;
        this.U_FriendshipPolicy = U_FriendshipPolicy;
        this.U_UserState = U_UserState;
    }


    public long getM_Id() {
        return m_id;
    }

    public void setM_Id(long m_Id) {
        this.m_id = m_Id;
    }

    public long getUu_id() {
        return uu_id;
    }

    public void setUu_id(long uu_id) {
        this.uu_id = uu_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public long getUser_register_date() {
        return user_register_date;
    }

    public void setUser_register_date(long user_register_date) {
        this.user_register_date = user_register_date;
    }

    public String getU_NationID() {
        return u_NationID;
    }

    public void setU_NationID(String u_NationID) {
        this.u_NationID = u_NationID;
    }

    public String getU_Province() {
        return u_Province;
    }

    public void setU_Province(String u_Province) {
        this.u_Province = u_Province;
    }

    public String getU_City() {
        return u_City;
    }

    public void setU_City(String u_City) {
        this.u_City = u_City;
    }

    public int getU_FriendshipPolicy() {
        return U_FriendshipPolicy;
    }

    public void setU_FriendshipPolicy(int u_FriendshipPolicy) {
        U_FriendshipPolicy = u_FriendshipPolicy;
    }

    public int getU_UserState() {
        return U_UserState;
    }

    public void setU_UserState(int u_UserState) {
        U_UserState = u_UserState;
    }

    public String getUser_name() {
        return this.user_name;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public int getUser_sex() {
        return this.user_sex;
    }


    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }


    public String getUser_real_name() {
        return this.user_real_name;
    }


    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }


    public String getUser_password() {
        return this.user_password;
    }


    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }


    public String getUser_desc() {
        return this.user_desc;
    }


    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }


    public String getUser_phone() {
        return this.user_phone;
    }


    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }


    public String getUser_account() {
        return this.user_account;
    }


    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }


    public String getUser_img_face_path() {
        return this.user_img_face_path;
    }


    public void setUser_img_face_path(String user_img_face_path) {
        this.user_img_face_path = user_img_face_path;
    }


    public long getM_id() {
        return this.m_id;
    }


    public void setM_id(long m_id) {
        this.m_id = m_id;
    }


    public String getAddress_message() {
        return address_message;
    }

    public void setAddress_message(String address_message) {
        this.address_message = address_message;
    }

    @Override
    public String toString() {
        return "User{" +
                "m_id=" + m_id +
                ", uu_id=" + uu_id +
                ", user_name='" + user_name + '\'' +
                ", user_sex=" + user_sex +
                ", user_real_name='" + user_real_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_desc='" + user_desc + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_account='" + user_account + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_img_face_path='" + user_img_face_path + '\'' +
                ", user_register_date=" + user_register_date +
                ", u_NationID='" + u_NationID + '\'' +
                ", u_Province='" + u_Province + '\'' +
                ", u_City='" + u_City + '\'' +
                ", address_message='" + address_message + '\'' +
                ", U_FriendshipPolicy=" + U_FriendshipPolicy +
                ", U_UserState=" + U_UserState +
                '}';
    }
}

