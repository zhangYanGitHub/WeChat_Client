package com.zhang.chat.net.interceptor;

import java.io.IOException;

import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import com.zhang.chat.utils.StrUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张俨 on 2017/7/28.
 * <p>
 * 统一进行公共参数添加的网络请求
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        oldRequest.header("Content-type:text/html;charset=utf-8");
        // 添加新的参数

        String preferStr = ShareUtil.getPreferStr(Constant.USER_NAME);

        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
        if (StrUtil.isNotBlank(preferStr)) {
            authorizedUrlBuilder.addQueryParameter("user_id", preferStr);
        }

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();
        AppLog.w("---------------------------------http request------------------\n\n" + newRequest.toString() + "\n\n---------------------------------http request------------------");
        return chain.proceed(newRequest);
    }
}
