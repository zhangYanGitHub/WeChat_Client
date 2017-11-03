package com.zhang.chat.utils;

import android.widget.Toast;


import com.zhang.chat.app.App;

/**
 * Created by junjun on 2017/7/25 0025.
 * 显示Toast
 */

public class AppToast {

    public static void showToast(String msg) {
        Toast.makeText(App.getApplication(),msg,Toast.LENGTH_SHORT).show();
    }
}
