package com.zhang.chat.bean;

import java.util.List;

/**
 * @Author: ZhangYan
 * @Description:
 * @Date Create In: 2017/9/25 21:11
 * @Modified By:
 */
public class ResList<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
