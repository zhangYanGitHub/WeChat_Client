package com.zhang.chat.net.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张俨 on 2017/9/19.
 */

public class ReceivedCookieIntercepter implements Interceptor {

    private static ReceivedCookieIntercepter instance;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie();
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
            AppLog.e("ReceivedCookieIntercepter  cookie = "+ cookie);
        }
        return chain.proceed(builder.build());
    }

    private String getCookie() {
        String token = ShareUtil.getPreferStr(Constant.COOKIES_TOKEN);
        return token;

    }
    public static Interceptor getInstance() {
        if (instance == null) {
            synchronized (ReceivedCookieIntercepter.class) {
                if (instance == null) {
                    instance = new ReceivedCookieIntercepter();
                }
            }
        }
        return instance;
    }
}
