package com.zhang.chat.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import com.zhang.chat.base.BaseFragment;

/**
 * Created by 张俨 on 2017/9/12.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragments;

    public MainPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
