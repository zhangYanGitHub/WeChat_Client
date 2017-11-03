package com.zhang.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.app.AppManager;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class CustomSubSearchBar extends RelativeLayout {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_image_clear)
    ImageView ivImageClear;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_image_search)
    ImageView ivImageSearch;
    @BindView(R.id.iv_image_voice)
    ImageView ivImageVioce;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    private Context context;

    public CustomSubSearchBar(Context context) {
        super(context);
        this.context = context;
        throw new RuntimeException("不支持该种构造");
    }


    public CustomSubSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initView(attrs);
    }

    public CustomSubSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        ;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.sub_main_search_bar, this, true);
        ButterKnife.bind(this);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchBar);
        if (attributes != null) {
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomSearchBar_background_search_title_bar, Color.WHITE);
            this.setBackgroundColor(titleBarBackGround);

            boolean isImage1Visible = attributes.getBoolean(R.styleable.CustomSearchBar_iv_voice_1_visible, false);
            ivImageVioce.setVisibility(isImage1Visible ? VISIBLE : GONE);
            tvSearch.setVisibility(GONE);
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivImageClear.setVisibility(VISIBLE);
                    tvSearch.setVisibility(VISIBLE);
                } else {
                    ivImageClear.setVisibility(GONE);
                    tvSearch.setVisibility(GONE);
                }
            }
        });

        ivImageClear.setOnClickListener(v -> {
            etSearch.setText("");
        });
        setImageBackListener(null);
    }

    public void setImageBackListener(final OnCLickListener imageBackListener) {
        ivBack.setOnClickListener(v -> {
            if (imageBackListener == null)
                AppManager.getAppManager().currentActivity().finish();
            else
                imageBackListener.onClick();
        });
    }

    /**
     * 设置editText 触摸按下的监听
     *
     * @param etSearchListener
     */
    private void setEtSearchListener(OnCLickListener etSearchListener) {
        etSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                etSearchListener.onClick();
            }
            return true;
        });

    }

    /**
     * 搜索监听
     *
     * @param etSearchListener
     */
    public void setEtSearchListener(OnClickListener etSearchListener) {
        if (etSearchListener != null) {
            tvSearch.setOnClickListener(v -> {
                etSearchListener.onClick(etSearch.getText().toString());
                etSearch.setText("");
            });
        }
    }

    public interface OnCLickListener {
        void onClick();
    }

    public interface OnClickListener {
        void onClick(String searchKey);
    }
}
