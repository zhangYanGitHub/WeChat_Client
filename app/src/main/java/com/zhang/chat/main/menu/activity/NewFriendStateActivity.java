package com.zhang.chat.main.menu.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;

import butterknife.BindView;

import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.corelib.recycleview.IRecyclerView;
import com.zhang.chat.corelib.recycleview.LoadMoreFooterView;
import com.zhang.chat.main.adapter.NewFriendStateAdapter;
import com.zhang.chat.main.chat.activity.FriendDataActivity;
import com.zhang.chat.main.menu.contract.NewFriendStateContract;
import com.zhang.chat.main.menu.model.NewFriendStateModel;
import com.zhang.chat.main.menu.presenter.NewFriendStatePresenter;
import com.zhang.chat.view.CustomSubTitleBar;
import com.zhang.chat.view.SubSearch;

public class NewFriendStateActivity extends BaseActivity<NewFriendStatePresenter, NewFriendStateModel>
        implements NewFriendStateContract.View {

    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.title_sub_bar1)
    SubSearch titleSubBar1;
    @BindView(R.id.recycle_view)
    IRecyclerView recycleView;
    private NewFriendStateAdapter adapter;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, NewFriendStateActivity.class);
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
        titleSubBar1.setEtSearchListener(() -> {
            SearchFriendActivity.startAction(this);
        });
        titleBar.setSaveBackgoudColor(Color.TRANSPARENT);
        titleBar.setSaveTextColor(R.color.Gray_999999);
        recycleView.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewFriendStateAdapter(this, mPresenter.getList());
        recycleView.setAdapter(adapter);
        mPresenter.getVerificationList();
        adapter.setOnItemClickListener(position -> {
            FriendDataActivity.startAction(NewFriendStateActivity.this, mPresenter.getFriend(mPresenter.getList().get(position)), 1);
        });
        adapter.setOnItemStateClickListener(position -> {
            mPresenter.setState(position);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_friend_state;
    }


    @Override
    public void notifyList() {
        adapter.notifyDataSetChanged();
    }
}
