package com.zhang.chat.splash;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.R;
import com.zhang.chat.login.LoginActivity;
import com.zhang.chat.main.MainActivity;

public class SplashActivity extends BaseActivity<SplashPresenter, SplashModel> implements SplashContract.View {


    @BindView(R.id.iv_splash)
    ImageView ivSplash;


    @Override
    protected void initData() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void doBefore() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.doBefore();
    }


    @Override
    public void jumpToLogin() {
        LoginActivity.startAction(this);
        finish();
    }

    @Override
    public void jumpToMain() {
        MainActivity.startAction(this);
        finish();
    }


}
