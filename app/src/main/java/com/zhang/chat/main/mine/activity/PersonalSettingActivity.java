package com.zhang.chat.main.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.User;
import com.zhang.chat.R;
import com.zhang.chat.corelib.pickphoto.ImageLoader;
import com.zhang.chat.corelib.pickphoto.ImgSelActivity;
import com.zhang.chat.corelib.pickphoto.ImgSelConfig;
import com.zhang.chat.corelib.utils.AppLog;
import com.zhang.chat.corelib.utils.ImageLoaderUtils;
import com.zhang.chat.main.mine.contract.MineContract;
import com.zhang.chat.main.mine.model.MineModel;
import com.zhang.chat.main.mine.presenter.MinePresenter;
import com.zhang.chat.net.UrlConstant;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.utils.LoadImage;
import com.zhang.chat.utils.StrUtil;
import com.zhang.chat.view.CustomSubTitleBar;


public class PersonalSettingActivity extends BaseActivity<MinePresenter, MineModel> implements MineContract.View {


    @BindView(R.id.iv_img_face)
    ImageView ivImgFace;
    @BindView(R.id.rl_person_img_face)
    RelativeLayout rlPersonImgFace;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_person_name)
    RelativeLayout rlPersonName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.rl_chat_account)
    RelativeLayout rlChatAccount;
    @BindView(R.id.iv_img_scan_card)
    ImageView ivImgScanCard;
    @BindView(R.id.rl_scan_card)
    RelativeLayout rlScanCard;
    @BindView(R.id.rl_more)
    RelativeLayout rlMore;
    @BindView(R.id.rl_my_address)
    RelativeLayout rlMyAddress;
    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;


    private int REQUEST_CODE = 120;
    private User user;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, PersonalSettingActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @OnClick({R.id.rl_person_img_face, R.id.rl_person_name, R.id.rl_chat_account, R.id.rl_scan_card, R.id.rl_my_address, R.id.rl_more})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_img_face:
                choosePhoto();
                break;
            case R.id.rl_person_name:
                PersonChangeActivity.startAction(this, MinePresenter.UPDATE_NAME);
                break;
            case R.id.rl_chat_account:
                break;
            case R.id.rl_scan_card:
                break;
            case R.id.rl_my_address:
                break;
            case R.id.rl_more:
                PersonalMoreSettingActivity.startAction(this);
                break;
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initData() {
        titleBar.setImageBackListener(() -> {
            finish();
        });
    }

    /**
     * 开启图片选择器
     */
    private void choosePhoto() {
        ImgSelConfig config = new ImgSelConfig.Builder(loader)
                // 是否多选
                .multiSelect(true)
                // 确定按钮背景色
                .btnBgColor(ContextCompat.getColor(this, R.color.main_color))
                .titleBgColor(ContextCompat.getColor(this, R.color.main_color))
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this, R.color.main_color))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back)
                .title("选择头像")
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    private ImageLoader loader = (ImageLoader) (context, path, imageView) -> {
        ImageLoaderUtils.display(context, imageView, path);
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (ListUtil.isNotEmpty(pathList)) {
                String s = pathList.get(0);
                LoadImage.loadFromLocal(this, ivImgFace, s);
                mPresenter.uploadFile(s, user.getM_Id() + "");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_setting;
    }


    @Override
    public void updateUserInfo(User user) {
        if (user == null) {
            AppLog.e(TAG + "  updateUserInfo() user == null");
            return;
        }
        this.user = user;
        if (StrUtil.isNotBlank(user.getUser_img_face_path()))
            LoadImage.loadFromUserImageFace(this, ivImgFace, String.valueOf(UrlConstant.BASE_FILE_URL+user.getUser_img_face_path()));
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
                    LoadImage.loadFromUserImageFace(this, ivImgFace, UrlConstant.BASE_FILE_URL +user.getUser_img_face_path());
                break;
        }
    }
    @Override
    protected void onStart() {
        if(mPresenter != null){
            mPresenter.onStart();
        }
        super.onStart();
    }


}