package com.zhang.chat.netty;

import com.zhang.chat.bean.chat.Message;

/**
 * Created by 张俨 on 2017/10/13.
 */

public interface ResponseListener {

    void Message(Message message);
}
