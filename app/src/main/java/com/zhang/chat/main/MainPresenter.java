package com.zhang.chat.main;

import android.content.Intent;

import com.zhang.chat.app.App;
import com.zhang.chat.netty.NettyService;

/**
 * Created by 张俨 on 2017/9/7.
 */

public class MainPresenter extends MainContract.Presenter {


    @Override
    public void onStart() {
        App.getApplication().startService(new Intent(App.getApplication(), NettyService.class));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getApplication().stopService(new Intent(App.getApplication(), NettyService.class));
    }


    @Override
    public void refresh() {
        int [] notices = mModel.getNotice();
        int num = 0;
        for(int i = 0;i<4  ;i++){
            mView.setDotNumber(i,notices[i]);
            num += notices[i];
        }
        mView.setTitleNum(num);
    }
}
