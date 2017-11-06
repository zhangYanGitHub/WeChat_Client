package com.zhang.chat.net;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class UrlConstant {

//        public static final String Host = "http:///192.168.31.123";
    public static final String Host = "http://192.168.6.10";
    //    public static final String Host = "http://192.168.1.105";
    public static final String PORT = ":8080";
    public static final String SOCKET_HOST = "192.168.6.10";
//        public static final String SOCKET_HOST = "192.168.31.123";
    public static final int SOCKET_PORT = 9000;

    public static final String BASE_FILE_URL = String.valueOf(Host + PORT + "/WeChat_Interface/");//文件服务器

    public static final String BASE_URL = String.valueOf(Host + PORT + "/WeChat_Interface/");//测试服务器
//

    /**
     * 登录
     */
    public static final String LOGIN = "user/login.action";

    /**
     * 更新用户信息
     */
    public static final String UPDATE_USER = "user/update.action";

    /**
     * 用户注册
     */
    public static final String USER_REGISTER = "user/register.action";


    /**
     * 用户注册
     */
    public static final String Friend_List = "friend/getFiendList.action";

    /**
     * 添加朋友
     */
    public static final String Friend_VerificationList = "friend/getVerificationList.action";
    /**
     * 搜索用户
     */
    public static final String Search_Friend = "friend/searchFriend.action";
    /**
     * 上传文件
     */
    public static final String FILE_UPLOAD = "file/upload.action";
    /**
     * 获取离线消息
     */
    public static final String MESSAGE_LIST = "message/getMessageList.action";
    /**
     * 获取离线消息
     */
    public static final String USER_DATA = "user/getUserData.action";


}
