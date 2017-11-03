package com.zhang.chat.main.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseFragment;
import com.zhang.chat.bean.User;
import com.zhang.chat.main.mine.activity.PersonalSettingActivity;
import com.zhang.chat.main.mine.activity.SystemSettingActivity;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.model.MineModel;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.StrUtil;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class MineFragment extends BaseFragment<MinePresenter, MineModel> implements MineContract.View {
    @BindView(R.id.iv_img_face)
    ImageView ivImgFace;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.in_money)
    ImageView inMoney;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.iv_cart_bag)
    ImageView ivCartBag;
    @BindView(R.id.tv_cart_bag)
    TextView tvCartBag;
    @BindView(R.id.iv_expression)
    ImageView ivExpression;
    @BindView(R.id.tv_expression)
    TextView tvExpression;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;


    @BindView(R.id.in_money_b)
    ImageView inMoneyB;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_money)
    RelativeLayout rlMoney;
    @BindView(R.id.iv_collect_b)
    ImageView ivCollectB;
    @BindView(R.id.iv_photo_b)
    ImageView ivPhotoB;
    @BindView(R.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R.id.iv_cart_bag_b)
    ImageView ivCartBagB;
    @BindView(R.id.rl_cart_bag)
    RelativeLayout rlCartBag;
    @BindView(R.id.iv_expression_b)
    ImageView ivExpressionB;
    @BindView(R.id.rl_expression)
    RelativeLayout rlExpression;
    @BindView(R.id.iv_setting_b)
    ImageView ivSettingB;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;


    private User user;


    @OnClick({R.id.ll_person_setting, R.id.rl_money, R.id.rl_collect, R.id.rl_photo, R.id.rl_cart_bag, R.id.rl_expression, R.id.rl_setting})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_person_setting: //个人信息设置
                PersonalSettingActivity.startAction(mActivity);
                break;
            case R.id.rl_money: //钱包信息设置
                break;
            case R.id.rl_collect: //收藏信息设置
                break;
            case R.id.rl_photo: //相册信息设置
                break;
            case R.id.rl_cart_bag: //卡包信息设置
                break;
            case R.id.rl_expression: //表情信息设置
                break;
            case R.id.rl_setting: //设置
                SystemSettingActivity.startAction(getActivity());
                break;
        }
    }

    @Override
    protected void initPresenter() {
        this.mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    public void updateUserInfo(User user) {
        this.user = user;
        if (user == null) return;
        if (StrUtil.isNotBlank(user.getUser_img_face_path()))
            LoadImage.loadFromUserImageFace(mActivity, ivImgFace, UrlConstant.BASE_FILE_URL + user.getUser_img_face_path());
        if (StrUtil.isNotBlank(user.getUser_name()))
            tvName.setText(user.getUser_name());
        if (StrUtil.isNotBlank(user.getUser_account()))
            tvAccount.setText(user.getUser_account());

    }

    @Override
    public void updateUserInfo(int type, User user) {
        switch (type) {
            case MinePresenter.UPDATE_ACCOUNT:
                if (StrUtil.isNotBlank(user.getUser_account()))
                    tvAccount.setText(user.getUser_account());
                break;
            case MinePresenter.UPDATE_NAME:
                if (StrUtil.isNotBlank(user.getUser_name()))
                    tvName.setText(user.getUser_name());
                break;
            case MinePresenter.UPDATE_IMGFACEPATH:
                if (StrUtil.isNotBlank(user.getUser_img_face_path()))
                    LoadImage.loadFromUserImageFace(mActivity, ivImgFace, UrlConstant.BASE_FILE_URL + user.getUser_img_face_path());
                break;
        }
    }

    @Override
    public void onResume() {
        mPresenter.onStart();
        super.onResume();
    }


}