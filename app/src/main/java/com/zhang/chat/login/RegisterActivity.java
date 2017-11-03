package com.zhang.chat.login;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.User;
import com.zhang.chat.dialog.MessageDialog;
import com.zhang.chat.login.contract.RegisterContract;
import com.zhang.chat.login.model.RegisterModel;
import com.zhang.chat.login.presenter.RegisterPresenter;
import com.zhang.chat.view.CustomSubTitleBar;

public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {


    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_real_name)
    EditText etUserRealName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    private int sex = 1;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @OnClick({R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                mPresenter.register();
                break;
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        rgSex.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_man:
                    sex = 1;
                    break;
                case R.id.radio_woman:
                    sex = 0;
                    break;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    public String getName() {
        return etUserName.getText().toString();
    }

    @Override
    public String getRealName() {
        return etUserRealName.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return etConfirmPassword.getText().toString();
    }

    @Override
    public int getSex() {
        return sex;
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public void sucess(User user) {
        MessageDialog dialog = MessageDialog.newInstance("你申请的账号", String.valueOf(user.getUu_id()), true);
        dialog.setOnOKClickListener(() -> {
            etConfirmPassword.postDelayed(() -> {
                LoginActivity.startAction(RegisterActivity.this, user);
                RegisterActivity.this.finish();
            }, 2000);
        });
        dialog.show(getSupportFragmentManager(), MessageDialog.TAG);
    }

    @Override
    public void showErrorMessage(String error) {
        Snackbar.make(etConfirmPassword, error, Snackbar.LENGTH_SHORT).show();
    }


}
