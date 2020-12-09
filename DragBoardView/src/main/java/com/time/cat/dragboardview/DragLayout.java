package com.time.cat.dragboardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.time.cat.dragboardview.helper.DragHelper;

public class DragLayout extends RelativeLayout {

    private DragHelper mDragHelper;

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDragHelper(DragHelper dragHelper) {
        mDragHelper = dragHelper;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDragHelper != null) {
            if (mDragHelper.isDraggingItem() || mDragHelper.isDraggingColumn()) {return true;}
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mDragHelper != null) {
                    mDragHelper.updateDraggingPosition(event.getRawX(), event.getRawY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mDragHelper != null) {
                    if (mDragHelper.isDraggingItem()) {
                        mDragHelper.dropItem();
                    } else if (mDragHelper.isDraggingColumn()) {
                        mDragHelper.dropCol();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
