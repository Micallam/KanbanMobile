package com.time.cat.dragboardview.callback;

import android.view.MotionEvent;
import android.view.View;

public interface onDragColumnListener {
    void onStartDragColumn(View columnView, int startPosition);
    void onDraggingColumn(View columnView, MotionEvent event);
    void onEndDragColumn(View columnView, int startPosition, int endPosition);
}
