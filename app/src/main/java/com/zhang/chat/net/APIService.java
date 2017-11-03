package com.zhang.chat.net;

import java.util.List;
import java.util.Map;

import com.zhang.chat.base.BaseResponse;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.MainData;
import com.zhang.chat.bean.ResList;
import com.zhang.chat.bean.User;
import com.zhang.chat.bean.chat.ListMessage;
import com.zhang.chat.bean.chat.Verification;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by 张俨 on 2017/9/8.
 */

public interface APIService {

    /**
     * 登录
     *
     * @param user
     * @param password
     * @return
     */
    @POST(UrlConstant.LOGIN)
    Observable<BaseResponse<User>> login(@Query("user_id") String user, @Query("user_password") String password);

    /**
     * 更新用户信息
     *
     * @param map
     * @return
     */
    @POST(UrlConstant.UPDATE_USER)
    Observable<BaseResponse<User>> updateUser(@QueryMap Map<String, String> map);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST(UrlConstant.USER_REGISTER)
    Observable<BaseResponse<User>> register(@QueryMap Map<String, String> map);

    /**
     * 获取联系人列表
     *
     * @return
     */
    @POST(UrlConstant.Friend_List)
    Observable<BaseResponse<ResList<Friend>>> getFriendList();

    /**
     * 获取离线消息
     *
     * @return
     */
    @POST(UrlConstant.MESSAGE_LIST)
    Observable<BaseResponse<ListMessage>> getMessageList();

    /**
     * 添加朋友验证消息
     *
     * @return
     */
    @POST(UrlConstant.Friend_VerificationList)
    Observable<BaseResponse<ResList<Verification>>> getVerificationList();

    /**
     * 添加朋友验证消息
     *
     * @return
     */
    @POST(UrlConstant.USER_DATA)
    Observable<BaseResponse<MainData>> getUserData(@Query("m_id") String m_id);



    /**
     * 搜索用户
     *
     * @return
     */
    @POST(UrlConstant.Search_Friend)
    Observable<BaseResponse<ResList<User>>> SearchFriend(@Query("search_key") String search_key,@Query("type") int type);

    @Multipart
    @POST(UrlConstant.FILE_UPLOAD)
    Observable<BaseResponse<String>> upload(@Part List<MultipartBody.Part> parts, @QueryMap Map<String, String> map);

}
