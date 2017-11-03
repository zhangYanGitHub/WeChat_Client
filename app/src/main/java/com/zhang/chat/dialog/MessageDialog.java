package com.zhang.chat.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.zhang.chat.R;
import com.zhang.chat.base.BaseDialogFragment;
import com.zhang.chat.utils.StrUtil;

/**
 * Create by ZhangYan on 2017/9/17.
 * <p>
 */

public class MessageDialog extends BaseDialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_cancel)
    Button btnCancle;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private Unbinder bind;

    public static MessageDialog newInstance(String title, String content, boolean isHideCancel) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putBoolean("isHideCancel", isHideCancel);
        MessageDialog fragment = new MessageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View inflate = inflater.inflate(R.layout.dialog_message, container, false);
        bind = ButterKnife.bind(this, inflate);
        Bundle arguments = getArguments();
        String title = arguments.getString("title");
        String content = arguments.getString("content");
        boolean isHideCancel = arguments.getBoolean("isHideCancel");
        if (StrUtil.isNotBlank(title)) {
            tvTitle.setText(title);
        }
        if (StrUtil.isNotBlank(content)) {
            tvContent.setText(content);
        }

        btnCancle.setVisibility(!isHideCancel ? View.VISIBLE : View.GONE);
        return inflate;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }


    private OnClickListener onOkClickListener;
    private OnClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnClickListener onCancleClickListener) {
        this.onCancelClickListener = onCancleClickListener;
    }

    public void setOnOKClickListener(OnClickListener onClickListener) {
        this.onOkClickListener = onClickListener;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if(onCancelClickListener != null)
                    onCancelClickListener.onClick();
                this.dismiss();
                break;
            case R.id.btn_register:
                if(onOkClickListener != null)
                    onOkClickListener.onClick();
                this.dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick();
    }



}
