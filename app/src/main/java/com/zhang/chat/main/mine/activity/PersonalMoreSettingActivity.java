package com.zhang.chat.main.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.User;
import com.zhang.chat.R;
import com.zhang.chat.dialog.PersonalSexDialog;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.model.MineModel;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.utils.StrUtil;
import com.zhang.chat.view.CustomSubTitleBar;


public class PersonalMoreSettingActivity extends BaseActivity<MinePresenter, MineModel>
        implements MineContract.View {


    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rl_desc)
    RelativeLayout rlDesc;
    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    private User user;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, PersonalMoreSettingActivity.class);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @OnClick({R.id.rl_sex, R.id.rl_address, R.id.rl_desc})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sex:
                showDialog1();
                break;
            case R.id.rl_address:
                SelectAddressActivity.startAction(this);
                break;
            case R.id.rl_desc:
                PersonChangeActivity.startAction(this, MinePresenter.UPDATE_DESC);
                break;

        }

    }

    private void showDialog1() {
        PersonalSexDialog dialog = new PersonalSexDialog();
        Bundle bundle = new Bundle();
        bundle.putString("sex", user.getUser_sex() + "");
        dialog.setArguments(bundle);

        dialog.setListener(radioId -> {
            switch (radioId) {
                case R.id.radio_man:
                    user.setUser_sex(1);
                    mPresenter.updateUser(MinePresenter.UPDATE_SEX, user);
                    break;
                case R.id.radio_woman:
                    user.setUser_sex(0);
                    mPresenter.updateUser(MinePresenter.UPDATE_SEX, user);
                    break;
            }
            dialog.dismiss();
        });
        dialog.show(getSupportFragmentManager(), PersonalSexDialog.TAG);
    }


    @Override
    protected void initData() {
        titleBar.setImageBackListener(() -> {
            finish();
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_more_setting;
    }


    @Override
    public void updateUserInfo(User user) {
        this.user = user;
        if (user == null) {
            return;
        }

        tvAddress.setText(String.valueOf((StrUtil.isBlank(user.getU_Province()) ? "" : user.getU_Province()) + " " + (StrUtil.isBlank(user.getU_City()) ? "" : user.getU_City())));
        if (user.getUser_sex() != -1)
            tvSex.setText((user.getUser_sex() == 1) ? "男" : "女");
        if (StrUtil.isNotBlank(user.getUser_desc()))
            tvDesc.setText(user.getUser_desc());
    }

    @Override
    public void updateUserInfo(int type, User user) {
        switch (type) {
            case MinePresenter.UPDATE_SEX:
                tvSex.setText((user.getUser_sex() == 1) ? "男" : "女");
                break;
            case MinePresenter.UPDATE_ADDRESS:
                break;
            case MinePresenter.UPDATE_DESC:
                tvDesc.setText(user.getUser_desc());
                break;
        }
    }

    @Override
    protected void onStart() {
        if (mPresenter != null) {
            mPresenter.onStart();
        }
        super.onStart();
    }

}
