package com.zhang.chat.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhang.chat.R;
import com.zhang.chat.utils.DensityUtil;
import com.zhang.chat.utils.ListUtil;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by 张俨 on 2017/9/12.
 */

public class TabRelativeLayout extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private final Context context;
    @BindView(R.id.iv_wx)
    ImageView ivWx;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.ll_wx)
    LinearLayout llWx;
    @BindView(R.id.iv_contact)
    ImageView ivContact;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.ll_find)
    LinearLayout llFind;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_dot_wx_1)
    TextView tvDotWx1;
    @BindView(R.id.ll_wx_dot_1)
    LinearLayout llWxDot1;
    @BindView(R.id.tv_dot_wx_2)
    TextView tvDotWx2;
    @BindView(R.id.ll_wx_dot_2)
    LinearLayout llWxDot2;
    @BindView(R.id.tv_contact_wx_1)
    TextView tvContactWx1;
    @BindView(R.id.ll_contact_dot_1)
    LinearLayout llContactDot1;
    @BindView(R.id.tv_contact_wx_2)
    TextView tvContactWx2;
    @BindView(R.id.ll_contact_dot_2)
    LinearLayout llContactDot2;
    @BindView(R.id.tv_find_wx_1)
    TextView tvFindWx1;
    @BindView(R.id.ll_find_dot_1)
    LinearLayout llFindDot1;
    @BindView(R.id.tv_find_wx_2)
    TextView tvFindWx2;
    @BindView(R.id.ll_find_dot_2)
    LinearLayout llFindDot2;
    @BindView(R.id.tv_mine_wx_1)
    TextView tvMineWx1;
    @BindView(R.id.ll_mine_dot_1)
    LinearLayout llMineDot1;
    @BindView(R.id.tv_mine_wx_2)
    TextView tvMineWx2;
    @BindView(R.id.ll_mine_dot_2)
    LinearLayout llMineDot2;


    private int[] mIconUnselectIds = {
            R.drawable.weixin_normal, R.drawable.contact_list_normal, R.drawable.find_normal, R.drawable.profile_normal};
    private int[] mIconSelectIds = {
            R.drawable.weixin_pressed, R.drawable.contact_list_pressed, R.drawable.find_pressed, R.drawable.profile_pressed};
    private List<ImageView> ImgViews = new ArrayList<>();
    private List<TextView> tvViews = new ArrayList<>();
    private List<TextView> dot1s = new ArrayList<>();
    private List<TextView> dot2s = new ArrayList<>();
    private ViewPager viewPager;


    public TabRelativeLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TabRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public TabRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }


    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.view_tab, this, true);
        ButterKnife.bind(this);
        ImgViews.add(ivWx);
        ImgViews.add(ivContact);
        ImgViews.add(ivFind);
        ImgViews.add(ivMine);

        tvViews.add(tvWx);
        tvViews.add(tvContact);
        tvViews.add(tvFind);
        tvViews.add(tvMine);


        dot1s.add(tvDotWx1);
        dot1s.add(tvContactWx1);
        dot1s.add(tvFindWx1);
        dot1s.add(tvMineWx1);

        dot2s.add(tvDotWx2);
        dot2s.add(tvContactWx2);
        dot2s.add(tvFindWx2);
        dot2s.add(tvMineWx2);

        setChecked(0);
    }

//    private QBadgeView initNotice(int position) {
//        QBadgeView qBadgeView = new QBadgeView(getContext());
//        qBadgeView.bindTarget(ImgViews.get(position))
//                .setBadgeGravity(Gravity.END | Gravity.TOP)
//                .setGravityOffset(10f, -10f, false)
//                .setBadgeBackgroundColor(getResources().getColor(R.color.Red_DC5D4A))
//                .setBadgeTextColor(getResources().getColor(R.color.White_FFFFFF)).hide(true);
//
//        return qBadgeView;
//
//    }


    public void setNotice(int index, int number) {
        if (number < 0) {
            dot1s.get(index).setVisibility(GONE);
            dot2s.get(index).setVisibility(VISIBLE);
        } else if (number == 0) {
            dot1s.get(index).setVisibility(GONE);
            dot2s.get(index).setVisibility(GONE);
        } else if (number > 0 && number < 100) {
            dot1s.get(index).setVisibility(VISIBLE);
            dot2s.get(index).setVisibility(GONE);
            dot1s.get(index).setText(String.valueOf(number));
        }else {
            dot1s.get(index).setVisibility(VISIBLE);
            dot2s.get(index).setVisibility(GONE);
            dot1s.get(index).setText(String.valueOf("99+"));
        }
    }


    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(this);
        }
    }

    public void setChecked(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                ImgViews.get(i).setImageResource(mIconSelectIds[i]);
                tvViews.get(i).setTextColor(getResources().getColor(R.color.Green_00A653));
            } else {
                ImgViews.get(i).setImageResource(mIconUnselectIds[i]);
                tvViews.get(i).setTextColor(getResources().getColor(R.color.Black_666666));
            }
        }
    }

    @OnClick({R.id.ll_wx, R.id.ll_contact, R.id.ll_find, R.id.ll_mine})
    public void onViewClicked(View view) {
        if (viewPager == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_wx:
                viewPager.setCurrentItem(0);
                setChecked(0);
                break;
            case R.id.ll_contact:
                viewPager.setCurrentItem(1);
                setChecked(1);
                break;
            case R.id.ll_find:
                viewPager.setCurrentItem(2);
                setChecked(2);
                break;
            case R.id.ll_mine:
                viewPager.setCurrentItem(3);
                setChecked(3);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setChecked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
