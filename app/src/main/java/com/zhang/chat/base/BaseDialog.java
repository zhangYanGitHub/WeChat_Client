package com.zhang.chat.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {

        super(context, themeResId);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}
