package com.zhang.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SideBar extends View {

    private Paint paint = new Paint();

    private boolean showBackground;

    public static String[] letters = {"↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "#"};

    private OnChooseLetterChangedListener onChooseLetterChangedListener;

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context) {
        this(context, null);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBackground) {
            // 字母条背后颜色
            canvas.drawColor(Color.parseColor("#BFBFBF"));
        }
        int height = getHeight();
        int width = getWidth();
        // 平均每个字母占的高度
        int singleHeight = height / letters.length;
        for (int i = 0; i < letters.length; i++) {
            // 字母的颜色
            paint.setColor(Color.parseColor("#474747"));
            paint.setAntiAlias(true);
            paint.setTextSize(25);
            float x = width / 2 - paint.measureText(letters[i]) / 2;
            float y = singleHeight * i + singleHeight;
            canvas.drawText(letters[i], x, y, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int c = (int) (y / getHeight() * letters.length);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                showBackground = true;
                if (c > -1 && c < letters.length && onChooseLetterChangedListener != null) {
                    onChooseLetterChangedListener.onChooseLetter(letters[c]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (c > -1 && c < letters.length && onChooseLetterChangedListener != null) {
                    onChooseLetterChangedListener.onChooseLetter(letters[c]);
                }
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                showBackground = false;
                if (onChooseLetterChangedListener != null) {
                    onChooseLetterChangedListener.onNoChooseLetter();
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnTouchingLetterChangedListener(OnChooseLetterChangedListener onChooseLetterChangedListener) {
        this.onChooseLetterChangedListener = onChooseLetterChangedListener;
    }

    public interface OnChooseLetterChangedListener {

        void onChooseLetter(String s);

        void onNoChooseLetter();

    }

}
