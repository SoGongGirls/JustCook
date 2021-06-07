package com.example.justcook.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import java.util.LinkedList;
import java.util.List;

/** 다른 클래스들에게 render callback을 제공하는 단순한 뷰입니다. */
public class OverlayView extends View {
    private final List<DrawCallback> callbacks = new LinkedList<DrawCallback>();

    public OverlayView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public void addCallback(final DrawCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public synchronized void draw(final Canvas canvas) {
        for (final DrawCallback callback : callbacks) {
            callback.drawCallback(canvas);
        }
    }

    /** Interface defining the callback for client classes. */
    public interface DrawCallback {
        public void drawCallback(final Canvas canvas);
    }
}