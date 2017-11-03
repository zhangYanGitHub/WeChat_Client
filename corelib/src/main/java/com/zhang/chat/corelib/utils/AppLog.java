package com.zhang.chat.corelib.utils;


import android.util.Log;


/**
 * 如果用于android平台，将信息记录到“LogCat”。如果用于java平台，将信息记录到“Console”
 * 使用logger封装
 */
public class AppLog {
    public static boolean DEBUG = true;

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(AppConstant.TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(AppConstant.TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(AppConstant.TAG, msg);
        }
    }

}
