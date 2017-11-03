package com.zhang.chat.dialog;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseDialogFragment;

/**
 * Create by ZhangYan on 2017/9/17.
 * <p>
 * 退出---修改性别
 */

public class ExitWeChatDialog extends BaseDialogFragment {

    @BindView(R.id.ll_exit_present_account)
    LinearLayout llExitPresentAccount;
    @BindView(R.id.ll_exit_we_chat)
    LinearLayout llExitWeChat;
    private Unbinder bind;

    /**
     * 退出微信
     */
    public static final int TYPE_EXIT_SYSTEM = 1;
    /**
     * 关闭当前账号
     */
    public static final int TYPE_EXIT_ACCOUNT = 2;

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View inflate = inflater.inflate(R.layout.dialog_exit, container, false);
        bind = ButterKnife.bind(this, inflate);

        return inflate;
    }


    @OnClick({R.id.ll_exit_present_account, R.id.ll_exit_we_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_exit_present_account://关闭当前账号：
                if (onClickListener != null) {
                    onClickListener.onClick(TYPE_EXIT_ACCOUNT);
                }
                break;
            case R.id.ll_exit_we_chat://退出微信
                if (onClickListener != null) {
                    onClickListener.onClick(TYPE_EXIT_SYSTEM);
                }
                break;
        }
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int type);
    }


    @Override
    public void onDestroyView() {
        bind.unbind();
        super.onDestroyView();
    }
}
