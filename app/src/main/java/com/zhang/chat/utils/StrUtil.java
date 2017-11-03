package com.zhang.chat.utils;

/**
 * Created by jun on 2017/08/01.
 * 这个类主要用于字符串的一些操作
 */
public class StrUtil {

    /**
     * 判断这个字符串是否为空
     * @return
     */
    public static boolean isBlank(String str) {
        if (null != str) {
            if (str.trim().length() == 0) {
                return true;
            } else {
                if (str.equalsIgnoreCase("null")) {
                    return true;
                }
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 判断这个字符串是否不为空
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
