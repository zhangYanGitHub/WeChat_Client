package com.zhang.chat.utils;

import java.util.List;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class ListUtil {

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

}
