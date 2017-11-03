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
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;

/**
 * Created by 张俨 on 2017/9/21.
 */

public class SubSearch extends RelativeLayout {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_image_clear)
    ImageView ivImageClear;
    @BindView(R.id.iv_image_search)
    ImageView ivImageSearch;
    private Context context;

    public SubSearch(Context context) {
        super(context);
    }

    public SubSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public SubSearch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.sub_search_bar, this, true);
        ButterKnife.bind(this);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchBar);
        if (attributes != null) {
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomSearchBar_background_search_title_bar, Color.WHITE);
            this.setBackgroundColor(titleBarBackGround);

            int resourceId = attributes.getResourceId(R.styleable.CustomSearchBar_search_image, R.drawable.search_sub);
            ivImageSearch.setImageResource(resourceId);



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
                if (s.length() > 1) {
                    ivImageClear.setVisibility(VISIBLE);
                } else {
                    ivImageClear.setVisibility(GONE);
                }
            }
        });

        ivImageClear.setOnClickListener(v -> {
            etSearch.setText("");
        });
    }

    /**
     * 设置editText 触摸按下的监听
     *
     * @param etSearchListener
     */
    public void setEtSearchListener(CustomSubSearchBar.OnCLickListener etSearchListener) {
        etSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                etSearchListener.onClick();
            }
            return true;
        });

    }
}
