package com.zhang.chat.main.menu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.bean.Friend;
import com.zhang.chat.bean.User;
import com.zhang.chat.corelib.recycleview.IRecyclerView;
import com.zhang.chat.main.adapter.SearchFriendAdapter;
import com.zhang.chat.main.chat.activity.FriendDataActivity;
import com.zhang.chat.main.menu.contract.SearchFriendContract;
import com.zhang.chat.main.menu.model.SearchFriendModel;
import com.zhang.chat.main.menu.presenter.SearchFriendPresenter;
import com.zhang.chat.utils.ListUtil;
import com.zhang.chat.view.CustomSubSearchBar;

public class SearchFriendActivity extends BaseActivity<SearchFriendPresenter, SearchFriendModel>
        implements SearchFriendContract.View, SearchFriendAdapter.OnClickListener {

    @BindView(R.id.title_bar)
    CustomSubSearchBar titleBar;
    @BindView(R.id.recycle_view)
    IRecyclerView recycleView;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.rl_reach_bottom)
    RelativeLayout rlReachBottom;
    private ArrayList<User> userInfos;
    private SearchFriendAdapter adapter;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SearchFriendActivity.class);
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
        userInfos = new ArrayList<>();
        recycleView.setRefreshEnabled(false);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchFriendAdapter(this, userInfos);
        recycleView.setAdapter(adapter);
        titleBar.setEtSearchListener(searchKey -> {
            mPresenter.searchFriend(searchKey);
        });
        adapter.setOnClickListener(this);
        rlReachBottom.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_friend;
    }


    @Override
    public void updateResult(List<User> userList) {
        tvDefault.setVisibility(View.GONE);
        userInfos.clear();
        userInfos.addAll(userList);
        adapter.notifyDataSetChanged();
        rlReachBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateEmpty() {
        recycleView.setVisibility(View.GONE);
        tvDefault.setVisibility(View.VISIBLE);
        rlReachBottom.setVisibility(View.GONE);
    }


    @Override
    public void onClick(int position) {
        if (ListUtil.isEmpty(userInfos) || userInfos.get(position) == null) return;
        Friend friend = new Friend(userInfos.get(0));

        FriendDataActivity.startAction(this, friend,1);
    }


}
