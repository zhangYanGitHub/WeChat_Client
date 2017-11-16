package com.zhang.chat.main.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.zhang.chat.R;
import com.zhang.chat.base.BaseActivity;
import com.zhang.chat.main.mine.contract.SelectAddressContract;
import com.zhang.chat.main.mine.model.SelectAddressModel;
import com.zhang.chat.main.mine.presenter.SelectAddressPresenter;
import com.zhang.chat.view.CustomSubTitleBar;

import butterknife.BindView;

public class SelectAddressActivity extends BaseActivity<SelectAddressPresenter, SelectAddressModel> implements SelectAddressContract.View {


    @BindView(R.id.title_bar)
    CustomSubTitleBar titleBar;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_all_address_name)
    TextView tvAllAddressName;
    @BindView(R.id.tv_all_address)
    TextView tvAllAddress;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SelectAddressActivity.class);
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
        tvAddress.setText("定位中...");
        mPresenter.startLocation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_address;
    }


    @Override
    public void locationFinish(String country, String provice, String city, String message) {
        tvAddress.setText(String.valueOf(country + "  " + provice + "  " + city + "  "));
    }

    @Override
    public void locationFail() {
        tvAddress.setText(String.valueOf("定位失败"));
    }


}
