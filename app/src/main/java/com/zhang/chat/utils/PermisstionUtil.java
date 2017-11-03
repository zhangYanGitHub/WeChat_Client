package com.zhang.chat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class PermisstionUtil {
    /**
     * 适配android 6.0 检查权限
     * android 6.0 的新特性，当APP需要使用一些敏感权限时，会对用户进行提示，同时代码中也要做相应处理
     */
    public static boolean checkPermission(Context context,String permission, int permissionCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions((Activity)context, new String[]{permission}, permissionCode);
            }
            return (ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
}
