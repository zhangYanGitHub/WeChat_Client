package com.zhang.chat.main.find;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseFragment;

/**
 * Created by 张俨 on 2017/9/11.
 */

public class FindFragment extends BaseFragment {
    @BindView(R.id.iv_circile)
    ImageView ivCircile;
    @BindView(R.id.iv_friend_circle_new)
    ImageView ivFriendCircleNew;
    @BindView(R.id.rl_friend_circle)
    RelativeLayout rlFriendCircle;
    @BindView(R.id.iv_sao)
    ImageView ivSao;
    @BindView(R.id.iv_scan_sao)
    ImageView ivScanSao;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R.id.iv_shake)
    ImageView ivShake;
    @BindView(R.id.iv_shake_b)
    ImageView ivShakeB;
    @BindView(R.id.rl_shake)
    RelativeLayout rlShake;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.iv_see_b)
    ImageView ivSeeB;
    @BindView(R.id.rl_scan_see)
    RelativeLayout rlScanSee;
    @BindView(R.id.iv_drift_bottle)
    ImageView ivDriftBottle;
    @BindView(R.id.iv_drift_bottle_b)
    ImageView ivDriftBottleB;
    @BindView(R.id.rl_drift_bottle)
    RelativeLayout rlDriftBottle;
    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.iv_shop_b)
    ImageView ivShopB;
    @BindView(R.id.rl_shopping)
    RelativeLayout rlShopping;
    @BindView(R.id.iv_game)
    ImageView ivGame;
    @BindView(R.id.iv_game_b)
    ImageView ivGameB;
    @BindView(R.id.rl_game)
    RelativeLayout rlGame;
    @BindView(R.id.iv_small_program)
    ImageView ivSmallProgram;
    @BindView(R.id.iv_small_program_b)
    ImageView ivSmallProgramB;
    @BindView(R.id.rl_small_program)
    RelativeLayout rlSmallProgram;
    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }



    @OnClick({R.id.rl_friend_circle, R.id.rl_scan, R.id.rl_shake, R.id.rl_scan_see, R.id.rl_drift_bottle, R.id.rl_shopping, R.id.rl_game, R.id.rl_small_program})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_friend_circle:
                break;
            case R.id.rl_scan:
                break;
            case R.id.rl_shake:
                break;
            case R.id.rl_scan_see:
                break;
            case R.id.rl_drift_bottle:
                break;
            case R.id.rl_shopping:
                break;
            case R.id.rl_game:
                break;
            case R.id.rl_small_program:
                break;
        }
    }
}
