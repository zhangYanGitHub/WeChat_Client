package com.zhang.chat.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.app.AppManager;
import com.zhang.chat.corelib.flyn.Eyes;
import com.zhang.chat.utils.TUtil;

/**
 * Created by 张俨 on 2017/9/7.
 */

public abstract class BaseActivity<T extends BasePresenter, M extends BaseModel> extends AppCompatActivity {

    protected T mPresenter;
    protected M mModel;
    protected String TAG;
    private boolean isConfigChange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        doBefore();
        TAG = getPackageName() + "." + getClass().getSimpleName();
        setContentView(layoutId);
        ButterKnife.bind(this);
        setStateBar();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.context = this;
        }
        this.initPresenter();

        initData();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    protected void doBefore() {
        //设置昼夜主题
        setTheme(R.style.AppTheme);
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
    }


    protected abstract void initData();

    protected void setStateBar() {
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.Gray_D7D7D7));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isConfigChange = true;
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }

        if (!isConfigChange) {
            AppManager.getAppManager().finishActivity(this);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyMessage((byte) msg.obj);
        }
    };

    public Handler getHandler() {
        return handler;
    }

    /**
     * socket消息分发
     *
     * @param type
     */
    public void notifyMessage(byte type) {

    }
}
