package com.zhang.chat.corelib.nativelib;

import android.graphics.Bitmap;

import com.zhang.chat.corelib.utils.AppLog;

/**
 * Created by 张俨 on 2017/10/23.
 */

public class GifHandler {
    public final static String TAG = GifHandler.class.getName();
    //代表C层的地址；
    private long gifPoint;

    public long getGifPoint() {
        return gifPoint;
    }

    public GifHandler(long gifPoint) {
        this.gifPoint = gifPoint;
    }

    static {
        try {
            System.loadLibrary("native-lib");
        } catch (Exception e) {
            AppLog.e(TAG + "   static initializer: " + e.toString());


        }
    }

    //    public static native int getWidth(gifPoint);
    public static native long loadGif(byte[] path);

    public static native int getWidth(long gifHandler);

    public static native int getHeight(long gifHandler);

    public static native int getNextTime(long gifHandler);

    public static native int updateFrame(long gifHandler, Bitmap bitmap);

    public static GifHandler load(String path) {
        long gifHander = loadGif(path.getBytes());
        GifHandler gifHandler = new GifHandler(gifHander);
        return gifHandler;
    }
}
