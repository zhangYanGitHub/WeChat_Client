package com.zhang.chat.main.menu.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.main.menu.contract.VerificationContract;
import com.zhang.chat.main.menu.model.VerificationModel;
import com.zhang.chat.main.menu.presenter.VerificationPresenter;
import com.zhang.chat.view.CustomSubTitleBar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VerificationActivity extends BaseActivity<VerificationPresenter, VerificationModel>
        implements VerificationContract.View {

    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_image_clear)
    ImageView ivImageClear;
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.iv_circle_turn)
    ImageView ivCircleTurn;
    private Friend friend;

    /**
     * 入口
     *
     * @param activity
     * @param friend
     */
    public static void startAction(Activity activity, Friend friend) {
        Intent intent = new Intent(activity, VerificationActivity.class);
        intent.putExtra("friend", friend);
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
        friend = (Friend) getIntent().getSerializableExtra("friend");
        if (friend == null) {
            finish();
            return;
        }
        User user = mPresenter.getUser();
        if (user != null) {
            etSearch.setHint(String.valueOf("我是" + user.getUser_name()));
        }
        etNickName.setText(friend.getUser_name());
        setImageCircleOff(true);

        titleBar.setOnSaveClickListener(new CustomSubTitleBar.OnCLickListener() {
            @Override
            public void onClick() {
                mPresenter.sendVerification();
                finish();
            }
        });
        titleBar.setSaveBackgoudColor(R.color.Green_00A653);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivImageClear.setVisibility(VISIBLE);
                } else {
                    ivImageClear.setVisibility(GONE);
                }
            }
        });

        ivImageClear.setOnClickListener(v -> {
            etSearch.setText("");
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    public Friend getFriend() {
        return friend;
    }

    @Override
    public String getMessage() {
        return etSearch.getText().toString();
    }

    /**
     * @param isOff true 不允许看朋友圈
     *              false 允许
     */
    private void setImageCircleOff(boolean isOff) {
        if (!isOff) {
            ivCircleTurn.setImageResource(R.drawable.off);
        } else {
            ivCircleTurn.setImageResource(R.drawable.on);
        }
    }

}
