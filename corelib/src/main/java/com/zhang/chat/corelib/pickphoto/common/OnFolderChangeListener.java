package com.zhang.chat.corelib.pickphoto.common;


import com.zhang.chat.corelib.pickphoto.bean.Folder;

/**
 * @author yuyh.
 * @date 2016/8/5.
 */
public interface OnFolderChangeListener {

    void onChange(int position, Folder folder);
}
