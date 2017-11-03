package com.zhang.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.zhang.chat.R;
import com.zhang.chat.app.AppManager;
import com.zhang.chat.utils.StrUtil;

/**
 * Create by ZhangYan on 2017/9/16.
 */

public class CustomSubTitleBar extends RelativeLayout {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.iv_image_1)
    ImageView ivImage1;
    @BindView(R.id.iv_image_2)
    ImageView ivImage2;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    private Context context;
    private OnCLickListener imageBackListener;

    public CustomSubTitleBar(Context context) {
        super(context);
        this.context = context;
        throw new RuntimeException("不支持该种构造");
    }


    public CustomSubTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ;
        initView(attrs);
    }

    public CustomSubTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        ;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.sub_main_title_bar, this, true);
        ButterKnife.bind(this);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        if (attributes != null) {
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomTitleBar_background_title_bar, Color.WHITE);
            this.setBackgroundColor(titleBarBackGround);

            boolean isImage1Visible = attributes.getBoolean(R.styleable.CustomTitleBar_right_1_visible, false);
            ivImage1.setVisibility(isImage1Visible ? VISIBLE : GONE);

            boolean isImage2Visible = attributes.getBoolean(R.styleable.CustomTitleBar_right_2_visible, false);
            ivImage2.setVisibility(isImage2Visible ? VISIBLE : GONE);
            boolean rightMore = attributes.getBoolean(R.styleable.CustomTitleBar_right_more_visible, false);
            ivMore.setVisibility(rightMore ? VISIBLE : GONE);

            boolean isSave2Visible = attributes.getBoolean(R.styleable.CustomTitleBar_right_save_visible, false);
            tvSave.setVisibility(isSave2Visible ? VISIBLE : GONE);


            int resourceId1 = attributes.getResourceId(R.styleable.CustomTitleBar_right_image_1, R.drawable.search);
            ivImage1.setImageResource(resourceId1);

            int resourceId2 = attributes.getResourceId(R.styleable.CustomTitleBar_right_image_2, R.drawable.search);
            ivImage2.setImageResource(resourceId2);

            String title = attributes.getString(R.styleable.CustomTitleBar_title_text);
            tvTitle.setText(title);

            String titleSave = attributes.getString(R.styleable.CustomTitleBar_text_save);
            tvSave.setText(StrUtil.isBlank(titleSave) ? "保存" : titleSave);

            int textSaveBack = attributes.getResourceId(R.styleable.CustomTitleBar_text_save_color, R.color.Green_00A653);
            tvSave.setBackgroundColor(textSaveBack);
            setImageBackListener(null);
        }
    }

    public void setTitleText(String title) {
        tvTitle.setText(title);
    }

    public void setImageBackListener(OnCLickListener imageBackListener) {
        this.imageBackListener = imageBackListener;
        ivBack.setOnClickListener(v -> {
            if (imageBackListener == null) {
                AppManager.getAppManager().currentActivity().finish();
            } else {
                imageBackListener.onClick();
            }
        });
    }

    public void setSaveBackgoudColor(int color) {
        tvSave.setBackgroundColor(color);
    }
    public void setSaveTextColor(int color) {
        tvSave.setTextColor(color);
    }
    public void setOnSaveClickListener(OnCLickListener saveClickListener) {
        tvSave.setOnClickListener(v -> {
            saveClickListener.onClick();
        });
    }

    public void setSaveEnable(boolean enable) {
        tvSave.setEnabled(enable);
    }

    /**
     * 更多菜单
     *
     * @param imMore
     */
    public void setIvMoreListener(OnCLickListener imMore) {
        ivImage1.setOnClickListener(v -> {
            if (imMore != null)
                imMore.onClick();

        });

    }

    public void setIvImage1Listener(OnCLickListener image1Listener) {
        ivImage1.setOnClickListener(v -> {
            if (image1Listener != null)
                image1Listener.onClick();

        });

    }

    public void setIvImage2Listener(OnCLickListener image2Listener) {
        ivImage2.setOnClickListener(v -> {
            image2Listener.onClick();
        });

    }

    public interface OnCLickListener {
        void onClick();
    }
}
