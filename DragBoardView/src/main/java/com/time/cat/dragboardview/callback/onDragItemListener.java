package com.time.cat.dragboardview.callback;

import android.view.MotionEvent;
import android.view.View;

public interface onDragItemListener {
    void onStartDragItem(View itemView, int startPosition);
    void onDraggingItem(View itemView, MotionEvent event);
    void onEndDragItem(View itemView, int startPosition, int endPosition);
}
