package com.zhang.chat.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.zhang.chat.utils.TUtil;

/**
 * Created by 张俨 on 2017/9/7.
 */

public abstract class BaseFragment<T extends BasePresenter, M extends BaseModel> extends Fragment {

    protected T mPresenter;
    protected M mModel;
    protected String TAG;
    protected Activity mActivity;
    private View mRootView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        mActivity = getActivity();
        TAG = mActivity.getPackageName() + "." + getClass().getSimpleName();
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutId, container, false);
        }
        bind = ButterKnife.bind(this, mRootView);

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.context = mActivity;
            this.initPresenter();
        }
        initData();
        return mRootView;
    }

    protected abstract void initPresenter();

    protected abstract void initData();

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    /**
     * socket消息分发
     *
     * @param type
     */
    public void notifyMessage(byte type) {

    }
}
