package com.zhang.chat.base;

/**
 * Created by 张俨 on 2017/9/8.
 */

public class BaseResponse<T> {
    private T data;
    private String info;
    private int code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean success() {
        return false;
    }
}
