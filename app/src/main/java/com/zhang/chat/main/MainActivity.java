package com.zhang.chat.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.base.BaseFragment;
import com.zhang.chat.main.adapter.MainPagerAdapter;
import com.zhang.chat.main.chat.ChatFragment;
import com.zhang.chat.main.contact.ContactFragment;
import com.zhang.chat.main.menu.activity.AddFriendActivity;
import com.zhang.chat.main.mine.MineFragment;
import com.zhang.chat.main.find.FindFragment;
import com.zhang.chat.popwindow.CommonPopupWindow;
import com.zhang.chat.view.TabRelativeLayout;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {


    @BindView(R.id.tv_left_title)
    TextView tvLeftTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tab_bar)
    TabRelativeLayout tabBar;
    private ChatFragment chatFragment;
    private ContactFragment contactFragment;
    private FindFragment findFragment;
    private MineFragment mineFragment;
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private CommonPopupWindow popupWindow;


    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }


    @OnClick({R.id.iv_search, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                break;
            case R.id.iv_more:
                showDownPop(ivSearch);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    //向下弹出
    public void showDownPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View inflate = LayoutInflater.from(this).inflate(R.layout.main_pop_window_menu, null, false);

        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(inflate)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setOutsideTouchable(true)
                .create();
        popWindowListener(inflate);
        popupWindow.showAsDropDown(view);

    }

    @Override
    protected void initData() {

        chatFragment = new ChatFragment();
        contactFragment = new ContactFragment();
        findFragment = new FindFragment();
        mineFragment = new MineFragment();

        fragments.add(chatFragment);
        fragments.add(contactFragment);
        fragments.add(findFragment);
        fragments.add(mineFragment);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
        tabBar.setViewPager(viewPager);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    public void popWindowListener(View view) {
        //添加朋友
        view.findViewById(R.id.ll_add_friend).setOnClickListener(v -> {
            AddFriendActivity.startAction(MainActivity.this);
            popupWindow.dismiss();
        });

        //发起群聊
        view.findViewById(R.id.ll_group_chat).setOnClickListener(v -> {
            popupWindow.dismiss();

        });

        //扫一扫
        view.findViewById(R.id.ll_scan).setOnClickListener(v -> {
            popupWindow.dismiss();

        });

        //帮助与反馈
        view.findViewById(R.id.ll_help).setOnClickListener(v -> {
            popupWindow.dismiss();

        });
    }


    @Override
    public void setDotNumber(int index, int number) {
        tabBar.setNotice(index, number);
    }

    @Override
    public MainContract.MainModel getMainModel() {
        return mModel;
    }

    @Override
    public void setTitleNum(int num) {
        if (num == 0) {
            tvLeftTitle.setText("微信");
        } else if (num < 100) {
            tvLeftTitle.setText("微信(" + num + ")");
        } else {
            tvLeftTitle.setText("微信(99+)");
        }
    }

    @Override
    public void notifyMessage(byte type) {
        for (BaseFragment fragment : fragments) {
            fragment.notifyMessage(type);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.refresh();
    }
}


