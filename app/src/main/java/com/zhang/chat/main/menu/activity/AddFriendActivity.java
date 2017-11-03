package com.zhang.chat.main.menu.activity;

import android.app.Activity;
import android.content.Intent;

import butterknife.BindView;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.view.CustomSubTitleBar;
import com.zhang.chat.view.SubSearch;

public class AddFriendActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.title_sub_bar1)
    SubSearch titleSubBar1;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, AddFriendActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initData() {
        titleSubBar1.setEtSearchListener(() -> {
            SearchFriendActivity.startAction(this);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }


}
