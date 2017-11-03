package com.zhang.chat.bean;

import com.zhang.chat.bean.chat.Header;

public class NettyHeader {
    private Header header;
    private String body;

    public Header getHeader() {
        return header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}

