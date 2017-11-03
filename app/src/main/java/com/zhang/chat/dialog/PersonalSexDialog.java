package com.zhang.chat.dialog;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.zhang.chat.base.BaseDialogFragment;
import com.zhang.chat.R;
import com.zhang.chat.utils.StrUtil;

/**
 * Create by ZhangYan on 2017/9/17.
 *
 * 个人信息---修改性别
 */

public class PersonalSexDialog extends BaseDialogFragment {
    @BindView(R.id.radio_man)
    RadioButton radioMan;
    @BindView(R.id.radio_woman)
    RadioButton radioWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    private OnRadioGroupListener listener;
    private Unbinder bind;

    public void setListener(OnRadioGroupListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View inflate = inflater.inflate(R.layout.dialog_person_sex, container, false);
        bind = ButterKnife.bind(this, inflate);
        String sex = getArguments().getString("sex");
        if (StrUtil.isNotBlank(sex) && "1".equals(sex)) {
            setRadioMan(true);
        } else {
            setRadioWoMan(true);
        }
        rgSex.setOnCheckedChangeListener((group, checkedId) -> {
            if (listener != null) {
                listener.onClick(checkedId);
            }
        });
        return inflate;
    }

    public interface OnRadioGroupListener {
        void onClick(@IdRes int radioId);
    }

    public void setRadioMan(boolean checked) {
        radioMan.setChecked(checked);
    }

    @Override
    public void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }

    public void setRadioWoMan(boolean cheaked) {
        radioWoman.setChecked(cheaked);
    }
}
