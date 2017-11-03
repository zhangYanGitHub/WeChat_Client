package com.zhang.chat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreference工具类
 */

public class ShareUtil {

    static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存字符串键值对
     *
     * @param key
     * @param value
     */
    public static void setPreferStr(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key获取字符串value
     *
     * @param key
     * @return
     */
    public static String getPreferStr(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static boolean getPreferBool(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setPreferBool(String key) {
        sharedPreferences.edit().putBoolean(key, true).commit();
    }

    public static long getPreferLong(String key) {
        return sharedPreferences.getLong(key,-1);
    }

    public static void setPreferLong(String key,long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }
}
