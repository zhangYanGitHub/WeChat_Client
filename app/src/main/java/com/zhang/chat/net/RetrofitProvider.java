package com.zhang.chat.net;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.zhang.chat.app.App;
import com.zhang.chat.net.interceptor.AddCookieIntercepter;
import com.zhang.chat.net.interceptor.ReceivedCookieIntercepter;
import com.zhang.chat.net.interceptor.RequestInterceptor;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class RetrofitProvider {

    private static APIService service;
    private static Retrofit retrofit;

    private static APIService upLoadService;
    private static Retrofit uploadRetrofit;

    private static APIService downLoadService;
    private static Retrofit downLoadRetrofit;

    //=====================upload======================//

    /**
     * 上传超时时间
     *
     * @param time
     * @return
     */
    public static APIService getUpLoadService(int time) {
        if (upLoadService == null) {
            synchronized (RetrofitProvider.class) {
                if (upLoadService == null) {
                    upLoadService = getUploadRetrofit(time).create(APIService.class);
                }
                return upLoadService;
            }
        }
        return upLoadService;
    }

    private static Retrofit getUploadRetrofit(int time) {
        if (uploadRetrofit == null) {
            //网络缓存路径文件
            File httpCacheDirectory = new File(App.getApplication().getExternalCacheDir(), "http");
            OkHttpClient client = new OkHttpClient.Builder()
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(time, TimeUnit.SECONDS)
                    .connectTimeout(time, TimeUnit.SECONDS)
//                    .cookieJar(new CookieManger(App.getApplication()))
                    .addInterceptor( new AddCookieIntercepter())
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(new RequestInterceptor())
                    .build();
            uploadRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(UrlConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return uploadRetrofit;
    }

    //=====================upload======================//

    //==========================================Json================//
    public static APIService getService() {
        if (service == null) {
            synchronized (RetrofitProvider.class) {
                if (service == null) {
                    service = getRetrofit().create(APIService.class);
                }
                return service;
            }
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            //网络缓存路径文件
            File httpCacheDirectory = new File(App.getApplication().getExternalCacheDir(), "http");
            OkHttpClient client = new OkHttpClient.Builder()
                    //设置cook的保存与提交
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(new ReceivedCookieIntercepter())
                    .addInterceptor(new AddCookieIntercepter())
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(UrlConstant.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //==========================================Json================//


    //=========================download================================//
    private static APIService getDownLoadService() {
        if (downLoadService == null) {
            synchronized (RetrofitProvider.class) {
                if (downLoadService == null) {
                    downLoadService = getDownLoadRetrofit().create(APIService.class);
                }
                return downLoadService;
            }
        }
        return downLoadService;
    }

    public static Retrofit getDownLoadRetrofit() {
        if (downLoadRetrofit == null) {
            //网络缓存路径文件
            //网络缓存路径文件
            File httpCacheDirectory = new File(App.getApplication().getExternalCacheDir(), "http");
            OkHttpClient client = new OkHttpClient.Builder()
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(new ReceivedCookieIntercepter())
                    .build();
            downLoadRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(UrlConstant.BASE_FILE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return downLoadRetrofit;
    }

    //=========================download================================//
}
