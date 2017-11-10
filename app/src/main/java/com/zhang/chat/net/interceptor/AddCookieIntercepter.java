package com.zhang.chat.net.interceptor;

import java.io.IOException;

import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.Constant;
import com.zhang.chat.utils.ShareUtil;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 张俨 on 2017/9/19.
 */

public class AddCookieIntercepter implements Interceptor {

    private static Interceptor instance;

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        Request request = chain.request();
        if (!request.url().toString().contains(UrlConstant.LOGIN)) {
            return originalResponse;
        }
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            String header = originalResponse.header("set-Cookie");
            ShareUtil.setPreferStr(Constant.COOKIES_TOKEN, header);
            AppLog.e("AddCookieIntercepter  cookie = " + header);
        }
        return originalResponse;
    }

    public static Interceptor getInstance() {
        if (instance == null) {
            synchronized (AddCookieIntercepter.class) {
                if (instance == null) {
                    instance = new AddCookieIntercepter();
                }
            }
        }
        return instance;
    }
}
