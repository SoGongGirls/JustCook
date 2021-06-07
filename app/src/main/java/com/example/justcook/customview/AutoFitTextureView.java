package com.example.justcook.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/** 지정된 가로 세로 비율로 조정할 수 있는 {@link TextureView} */
public class AutoFitTextureView extends TextureView {
    private int ratioWidth = 0;
    private int ratioHeight = 0;

    public AutoFitTextureView(final Context context) { this(context, null); }

    public AutoFitTextureView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 이 뷰의 가로 세로 비율을 설정합니다. 비율에 따라 뷰의 크기가 측정됩니다.
     * 매개변수로부터 계산됩니다. 실제 매개변수의 크기는 중요하지 않습니다. 즉,
     * setAspectRatio(2,3)와 setAspectRatio(4,6)를 호출해도 같은 결과가 됩니다.
     *
     * @param width Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(final int width, final int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        ratioWidth = width;
        ratioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == ratioWidth || 0 == ratioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * ratioWidth / ratioHeight) {
                setMeasuredDimension(width, width * ratioHeight / ratioWidth);
            } else {
                setMeasuredDimension(height * ratioWidth / ratioHeight, height);
            }
        }
    }
}