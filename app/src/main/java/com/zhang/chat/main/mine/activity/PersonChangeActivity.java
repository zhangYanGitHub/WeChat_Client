package com.zhang.chat.main.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.User;
import com.zhang.chat.R;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.model.MineModel;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.utils.StrUtil;
import com.zhang.chat.view.CustomSubTitleBar;


public class PersonChangeActivity extends BaseActivity<MinePresenter, MineModel> implements MineContract.View {


    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_name_hint)
    TextView tvNameHint;
    private User user;
    private int updateType;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int type) {
        Intent intent = new Intent(activity, PersonChangeActivity.class);
        intent.putExtra("update_type", type);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        updateType = getIntent().getIntExtra("update_type", -1);
        switch (updateType) {
            case MinePresenter.UPDATE_DESC:
                titleBar.setTitleText("个性签名");
                tvNameHint.setVisibility(View.GONE);
                etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
                break;
            case MinePresenter.UPDATE_NAME:
                titleBar.setTitleText("更改名字");
                tvNameHint.setVisibility(View.VISIBLE);
                break;
        }
        titleBar.setOnSaveClickListener(() -> {
            switch (updateType) {
                case MinePresenter.UPDATE_NAME:
                    user.setUser_name(etName.getText().toString());
                    mPresenter.updateUser(MinePresenter.UPDATE_NAME, user);
                    break;
                case MinePresenter.UPDATE_DESC:
                    user.setUser_desc(etName.getText().toString());
                    mPresenter.updateUser(MinePresenter.UPDATE_DESC, user);
                    break;

            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if ((StrUtil.isNotBlank(s1) && s1.equals(user.getUser_name())) || StrUtil.isBlank(s1)) {
                    titleBar.setSaveEnable(false);
                    titleBar.setSaveBackgoudColor(PersonChangeActivity.this.getResources().getColor(R.color.Black_000000_70));
                } else {
                    titleBar.setSaveEnable(true);
                    titleBar.setSaveBackgoudColor(PersonChangeActivity.this.getResources().getColor(R.color.Green_00B657));
                }
                if (updateType == MinePresenter.UPDATE_DESC && s.length() > 30) {
                    Snackbar.make(etName, "个性签名最大字数为30", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_change;
    }

    @Override
    public void updateUserInfo(User user) {
        this.user = user;
        if (user == null) return;
        switch (updateType) {
            case MinePresenter.UPDATE_NAME:
                if (StrUtil.isNotBlank(user.getUser_name()))
                    etName.setText(user.getUser_name());
                break;
            case MinePresenter.UPDATE_DESC:
                if (StrUtil.isNotBlank(user.getUser_desc()))
                    etName.setText(user.getUser_desc());
                break;
        }
    }

    @Override
    public void updateUserInfo(int type, User user) {
        finish();
    }


}
