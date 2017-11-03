package com.zhang.chat.main.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.dialog.ExitWeChatDialog;
import com.zhang.chat.login.LoginActivity;
import com.zhang.chat.main.mine.contract.SystemContract;
import com.zhang.chat.main.mine.model.SystemModel;
import com.zhang.chat.main.mine.presenter.SystemPresenter;
import com.zhang.chat.view.CustomSubTitleBar;

/**
 * 系统设置
 */
public class SystemSettingActivity extends BaseActivity<SystemPresenter, SystemModel>
        implements SystemContract.View {
    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.tv_message_hint)
    TextView tvMessageHint;
    @BindView(R.id.rl_message_hint)
    RelativeLayout rlMessageHint;
    @BindView(R.id.tv_dnd_mode)
    TextView tvDndMode;
    @BindView(R.id.rl_dnd_mode)
    RelativeLayout rlDndMode;
    @BindView(R.id.tv_chat)
    TextView tvChat;
    @BindView(R.id.rl_chat)
    RelativeLayout rlChat;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    @BindView(R.id.rl_privacy)
    RelativeLayout rlPrivacy;
    @BindView(R.id.tv_common)
    TextView tvCommon;
    @BindView(R.id.rl_common)
    RelativeLayout rlCommon;
    @BindView(R.id.tv_security)
    TextView tvSecurity;
    @BindView(R.id.rl_security)
    RelativeLayout rlSecurity;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.rl_help)
    RelativeLayout rlHelp;
    @BindView(R.id.tv_plug_in)
    TextView tvPlugIn;
    @BindView(R.id.rl_plug_in)
    RelativeLayout rlPlugIn;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SystemSettingActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @OnClick({R.id.rl_message_hint, R.id.rl_dnd_mode, R.id.rl_chat, R.id.rl_privacy, R.id.rl_common
            , R.id.rl_security, R.id.rl_about, R.id.rl_help, R.id.rl_plug_in, R.id.rl_exit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_message_hint: //新消息提醒
                break;
            case R.id.rl_dnd_mode: //钱包信息设置
                break;
            case R.id.rl_chat: //聊天
                break;
            case R.id.rl_privacy: //隐私设置
                break;
            case R.id.rl_common: //通用
                break;
            case R.id.rl_security: //帐号与安全
                break;
            case R.id.rl_about: //关于我们
                break;
            case R.id.rl_help: //帮助
                break;
            case R.id.rl_plug_in: //插件
                break;
            case R.id.rl_exit: //退出
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        ExitWeChatDialog exitWeChatDialog = new ExitWeChatDialog();
        exitWeChatDialog.setOnClickListener(type -> {
            switch (type) {
                case ExitWeChatDialog.TYPE_EXIT_ACCOUNT:
                    exitWeChatDialog.dismiss();
                    mPresenter.clearData();
                    break;
                case ExitWeChatDialog.TYPE_EXIT_SYSTEM:
                    exitWeChatDialog.dismiss();
                    mPresenter.exitApp();
                    break;
            }
        });
        exitWeChatDialog.show(getSupportFragmentManager(), ExitWeChatDialog.TAG);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_setting;
    }


    @Override
    public void goToLogin() {
        LoginActivity.startAction(this);
        finish();
    }
}
