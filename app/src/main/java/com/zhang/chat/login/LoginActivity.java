package com.zhang.chat.login;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.User;
import com.zhang.chat.R;
import com.zhang.chat.login.contract.LoginContract;
import com.zhang.chat.login.model.LoginModel;
import com.zhang.chat.login.presenter.LoginPresenter;
import com.zhang.chat.main.MainActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.ll_login_form)
    LinearLayout llLoginForm;
    @BindView(R.id.login_form)
    ScrollView loginForm;

    private User user;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, User user) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("user", user);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.tv_forget_password})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login();
                break;
            case R.id.btn_register:
                RegisterActivity.startAction(this);
                break;
            case R.id.tv_forget_password:
                break;
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        if (getIntent().hasExtra("user")) {
            User user = (User) getIntent().getSerializableExtra("user");
            if (user != null) {
                etName.setText(String.valueOf(user.getM_Id()));
                etPassword.setText(user.getUser_password());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public String getName() {
        String name = etName.getText().toString();
        return name;
    }

    @Override
    public String getPassword() {
        String password = etPassword.getText().toString();
        return password;
    }

    @Override
    public void showErrorMessage(String error) {
        Snackbar.make(etName, error, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void update() {
        showErrorMessage("登录成功");
        etPassword.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.startAction(LoginActivity.this);
                LoginActivity.this.finish();
            }
        }, 3 * 1000);
    }
}

