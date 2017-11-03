package com.zhang.chat.main.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.main.chat.contract.FriendDataContract;
import com.zhang.chat.main.chat.model.FriendDataModel;
import com.zhang.chat.main.chat.presenter.FriendDataPresenter;
import com.zhang.chat.main.menu.activity.VerificationActivity;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.StrUtil;
import com.zhang.chat.view.CustomSubTitleBar;

public class FriendDataActivity extends BaseActivity<FriendDataPresenter, FriendDataModel>
        implements FriendDataContract.View {

    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.iv_img_face)
    ImageView ivImgFace;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_man_or_woman)
    ImageView ivManOrWoman;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_wx_account)
    LinearLayout llWxAccount;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.ll_nick_name)
    LinearLayout llNickName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.tv_photo1)
    ImageView tvPhoto1;
    @BindView(R.id.tv_photo2)
    ImageView tvPhoto2;
    @BindView(R.id.tv_photo3)
    ImageView tvPhoto3;
    @BindView(R.id.ll_photo)
    LinearLayout llPhoto;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.btn_send_message)
    Button btnSendMessage;
    @BindView(R.id.btn_video_chat)
    Button btnVideoChat;
    @BindView(R.id.rl_wx)
    RelativeLayout rlWx;
    @BindView(R.id.ll_friend_data)
    LinearLayout llFriendData;
    @BindView(R.id.btn_send_verification_message)
    Button btnSendVerificationMessage;
    @BindView(R.id.ll_verification)
    LinearLayout llVerification;
    private Friend friend;
    private int type;

    /**
     * 入口
     *
     * @param activity
     * @param friend
     * @param type     0  朋友资料
     *                 1 朋友验证资料
     *                 2
     */
    public static void startAction(Activity activity, Friend friend, int type) {
        Intent intent = new Intent(activity, FriendDataActivity.class);
        intent.putExtra("friend", friend);
        intent.putExtra("type", type);
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
        //更多菜单
        titleBar.setIvMoreListener(() -> {

        });
        friend = (Friend) getIntent().getSerializableExtra("friend");
        if (friend == null) {
            finish();
            return;
        }
        type = getIntent().getIntExtra("type", -1);
        initFriendData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_data;
    }


    @OnClick({R.id.ll_phone, R.id.ll_sign, R.id.ll_photo, R.id.ll_more, R.id.btn_send_message, R.id.btn_video_chat, R.id.btn_send_verification_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_phone:
                break;
            case R.id.ll_sign:
                break;
            case R.id.ll_photo:
                break;
            case R.id.ll_more:
                break;
            case R.id.btn_send_message:
                ChatActivity.startAction(this, friend);
                finish();
                break;
            case R.id.btn_video_chat:
                break;
            case R.id.btn_send_verification_message:
                VerificationActivity.startAction(this, friend);
                break;
        }
    }


    public void initFriendData() {


        if (StrUtil.isNotBlank(friend.getUser_img_face_path())) {
            LoadImage.loadFromUserImageFace(this, ivImgFace, UrlConstant.BASE_FILE_URL + friend.getUser_img_face_path());
        }

        if (StrUtil.isNotBlank(friend.getUser_name())) {
            tvName.setText(friend.getUser_name());
        }
        if (StrUtil.isNotBlank(friend.getF_friend_type_id())) {
            llNickName.setVisibility(View.VISIBLE);
            tvNickName.setText(friend.getF_friend_type_id());
        } else
            llNickName.setVisibility(View.GONE);

        if (StrUtil.isNotBlank(friend.getUser_phone())) {
            tvPhone.setText(friend.getUser_phone());
        } else
            llPhone.setVisibility(View.GONE);

        ivManOrWoman.setImageResource(friend.getUser_sex() == 1 ? R.drawable.man : R.drawable.woman);
        switch (type) {// 0  朋友资料    1 朋友验证资料

            case 0:
                llFriendData.setVisibility(View.VISIBLE);
                llVerification.setVisibility(View.GONE);
                llMore.setVisibility(View.VISIBLE);
                llPhoto.setVisibility(View.VISIBLE);
                break;
            case 1:
                llFriendData.setVisibility(View.GONE);
                llVerification.setVisibility(View.VISIBLE);
                llMore.setVisibility(View.GONE);
                llPhoto.setVisibility(View.GONE);
                break;
        }

    }


}
