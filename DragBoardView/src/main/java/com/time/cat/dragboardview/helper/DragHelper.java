package com.time.cat.dragboardview.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.time.cat.dragboardview.DragLayout;
import com.time.cat.dragboardview.PagerRecyclerView;
import com.time.cat.dragboardview.callback.DragHorizontalAdapter;
import com.time.cat.dragboardview.callback.DragHorizontalViewHolder;
import com.time.cat.dragboardview.callback.DragVerticalAdapter;
import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;
import com.time.cat.dragboardview.utils.AttrAboutPhone;

import java.util.Timer;
import java.util.TimerTask;

public class DragHelper {
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_FOOTER = 2;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private ImageView mDragImageView;
    private RecyclerView mCurrentVerticalRecycleView;
    private PagerRecyclerView mHorizontalRecyclerView;

    private DragItem dragItem;
    private boolean isDraggingItem = false;
    private DragColumn dragColumn;
    private boolean isDraggingColumn = false;

    private float mBornLocationX, mBornLocationY;
    private int offsetX, offsetY;
    private boolean confirmOffset = false;

    private Timer mHorizontalScrollTimer = new Timer();
    private TimerTask mHorizontalScrollTimerTask;
    private static final int HORIZONTAL_STEP = 30;
    private static final int HORIZONTAL_SCROLL_PERIOD = 20;
    private int leftScrollBounce;
    private int rightScrollBounce;

    private Timer mVerticalScrollTimer = new Timer();
    private TimerTask mVerticalScrollTimerTask;
    private static final int VERTICAL_STEP = 10;
    private static final int VERTICAL_SCROLL_PERIOD = 10;
    private int upScrollBounce;
    private int downScrollBounce;
    private int mPosition = -1;
    private int mPagerPosition = -1;


    @SuppressLint("ClickableViewAccessibility")
    public DragHelper(Context activity) {
        mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        mWindowParams.alpha = 1.0f;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowParams.x = 0;
        mWindowParams.y = 0;

        mDragImageView = new ImageView(activity);
        mDragImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDragImageView.setPadding(10, 10, 10, 10);
        mDragImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isDraggingItem) {
                        dropItem();
                    } else if (isDraggingColumn) {
                        dropCol();
                    }
                }
                return false;
            }
        });
    }


    public boolean isDraggingColumn() {
        return isDraggingColumn;
    }

    public void dragCol(View columnView, int position) {
        columnView.destroyDrawingCache();
        columnView.setDrawingCacheEnabled(true);
        Bitmap bitmap = columnView.getDrawingCache();
        if (bitmap != null && !bitmap.isRecycled()) {
            mDragImageView.setImageBitmap(bitmap);
            mDragImageView.setRotation(1.5f);
            mDragImageView.setAlpha(0.8f);

            isDraggingColumn = true;

            if (columnView.getTag() instanceof DragColumn) {
                dragColumn = (DragColumn) columnView.getTag();
            }
            int dragPage = mHorizontalRecyclerView.getCurrentPosition();
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) mHorizontalRecyclerView.findViewHolderForAdapterPosition(dragPage);
            if (holder != null && holder.itemView != null && holder.getItemViewType() == TYPE_CONTENT) {
                mCurrentVerticalRecycleView = ((DragHorizontalViewHolder) holder).getRecyclerView();
                mPagerPosition = dragPage;
            }

            getTargetHorizontalRecyclerViewScrollBoundaries();
            getTargetVerticalRecyclerViewScrollBoundaries();

            int[] location = new int[2];
            columnView.getLocationOnScreen(location);
            mWindowParams.x = location[0];
            mWindowParams.y = location[1];
            mBornLocationX = location[0];
            mBornLocationY = location[1];
            confirmOffset = false;
            mPosition = position;
            mWindowManager.addView(mDragImageView, mWindowParams);
            getHorizontalAdapter().onDrag(position);
        }
    }

    public void dropCol() {
        if (isDraggingColumn) {
            mWindowManager.removeView(mDragImageView);
            isDraggingColumn = false;

            if (mVerticalScrollTimerTask != null) {
                mVerticalScrollTimerTask.cancel();
            }

            if (mHorizontalScrollTimerTask != null) {
                mHorizontalScrollTimerTask.cancel();
            }

            if (mHorizontalRecyclerView != null) {
                mHorizontalRecyclerView.backToCurrentPage();
            }

            getHorizontalAdapter().onDrop(mPagerPosition, mPosition, dragColumn);
        }
    }

    private void updateSlidingHorizontalRecyclerView(float x, float y) {
        int newPage = getHorizontalCurrentPosition(x, y);
        if (mPagerPosition != newPage) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) mHorizontalRecyclerView.findViewHolderForAdapterPosition(newPage);
            if (holder != null && holder.itemView != null && holder.getItemViewType() == TYPE_CONTENT) {
                getHorizontalAdapter().onDragOut();
                mCurrentVerticalRecycleView = ((DragHorizontalViewHolder) holder).getRecyclerView();
                mPagerPosition = newPage;
                getHorizontalAdapter().onDragIn(mPosition, dragColumn);

            }
        }
    }

    private void findViewPositionInHorizontalRV(float rowX, float rowY) {
        int[] location = new int[2];
        mHorizontalRecyclerView.getLocationOnScreen(location);
        float x = rowX - location[0];
        float y = rowY - location[1];
        View child = mHorizontalRecyclerView.findChildViewUnder(x, y);
        int newPosition = mHorizontalRecyclerView.getChildAdapterPosition(child);
        int footerChildIndex = mHorizontalRecyclerView.getChildCount() + 1;
        if (newPosition != RecyclerView.NO_POSITION
                && newPosition != footerChildIndex) {
            getHorizontalAdapter().updateDragItemVisibility(mPosition);
            if (mPosition != newPosition && mPosition < footerChildIndex) {
                mPosition = newPosition;
            }
        }
    }

    private DragHorizontalAdapter getHorizontalAdapter() {
        return (DragHorizontalAdapter) mHorizontalRecyclerView.getAdapter();
    }

    public void bindHorizontalRecyclerView(@NonNull PagerRecyclerView view) {
        mHorizontalRecyclerView = view;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new RuntimeException("LayoutManager must be LinearLayoutManager");
        }
    }
    //endregion


    //region drag item

    public boolean isDraggingItem() {
        return isDraggingItem;
    }

    public void dragItem(View dragger, int position) {
        dragger.destroyDrawingCache();
        dragger.setDrawingCacheEnabled(true);
        Bitmap bitmap = dragger.getDrawingCache();
        if (bitmap != null && !bitmap.isRecycled()) {
            mDragImageView.setImageBitmap(bitmap);
            mDragImageView.setRotation(1.5f);
            mDragImageView.setAlpha(0.8f);

            isDraggingItem = true;
            if (dragger.getTag() instanceof DragItem) {
                dragItem = (DragItem) dragger.getTag();
            }
            int dragPage = mHorizontalRecyclerView.getCurrentPosition();
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) mHorizontalRecyclerView.findViewHolderForAdapterPosition(dragPage);
            if (holder != null && holder.itemView != null && holder.getItemViewType() == TYPE_CONTENT) {
                mCurrentVerticalRecycleView = ((DragHorizontalViewHolder) holder).getRecyclerView();

                mPagerPosition = dragPage;
            }

            getTargetHorizontalRecyclerViewScrollBoundaries();
            getTargetVerticalRecyclerViewScrollBoundaries();

            int[] location = new int[2];
            dragger.getLocationOnScreen(location);
            mWindowParams.x = location[0];
            mWindowParams.y = location[1];
            mBornLocationX = location[0];
            mBornLocationY = location[1];
            confirmOffset = false;
            mPosition = position;
            mWindowManager.addView(mDragImageView, mWindowParams);
            getCurrentVerticalAdapter().onDrag(position);
        }
    }

    public void dropItem() {
        if (isDraggingItem) {
            mWindowManager.removeView(mDragImageView);
            isDraggingItem = false;

            if (mVerticalScrollTimerTask != null) {
                mVerticalScrollTimerTask.cancel();
            }

            if (mHorizontalScrollTimerTask != null) {
                mHorizontalScrollTimerTask.cancel();
            }

            if (mHorizontalRecyclerView != null) {
                mHorizontalRecyclerView.backToCurrentPage();
            }

            getCurrentVerticalAdapter().onDrop(mPagerPosition, mPosition, dragItem);
        }
    }

    private void updateSlidingVerticalRecyclerView(float x, float y) {
        int newPage = getHorizontalCurrentPosition(x, y);
        if (mPagerPosition != newPage) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) mHorizontalRecyclerView.findViewHolderForAdapterPosition(newPage);
            if (holder != null && holder.itemView != null && holder.getItemViewType() == TYPE_CONTENT) {
                getCurrentVerticalAdapter().onDragOut();

                mCurrentVerticalRecycleView = ((DragHorizontalViewHolder) holder).getRecyclerView();
                mPagerPosition = newPage;

                getCurrentVerticalAdapter().onDragIn(mPosition, dragItem);
            }
        }
    }

    private void findViewPositionInCurVerticalRV(float rowX, float rowY) {
        int[] location = new int[2];
        mCurrentVerticalRecycleView.getLocationOnScreen(location);
        float x = rowX - location[0];
        float y = rowY - location[1];
        View child = mCurrentVerticalRecycleView.findChildViewUnder(x, y);
        int newPosition = mCurrentVerticalRecycleView.getChildAdapterPosition(child);
        if (newPosition != RecyclerView.NO_POSITION) {
            getCurrentVerticalAdapter().updateDragItemVisibility(mPosition);
            if (mPosition != newPosition) {
                mPosition = newPosition;
            }
        }
    }

    private DragVerticalAdapter getCurrentVerticalAdapter() {
        return (DragVerticalAdapter) mCurrentVerticalRecycleView.getAdapter();
    }

    public void updateDraggingPosition(float rowX, float rowY) {
        if (mWindowManager == null || mWindowParams == null)
            return;
        if (!confirmOffset) {
            calculateOffset(rowX, rowY);
        }
        if (isDraggingItem) {
            mWindowParams.x = (int) (rowX - offsetX);
            mWindowParams.y = (int) (rowY - offsetY);
            mWindowManager.updateViewLayout(mDragImageView, mWindowParams);
            updateSlidingVerticalRecyclerView(rowX, rowY);
            findViewPositionInCurVerticalRV(rowX, rowY);
            recyclerViewScrollHorizontal((int) rowX, (int) rowY);
            recyclerViewScrollVertical((int) rowX, (int) rowY);
        } else if (isDraggingColumn) {
            mWindowParams.x = (int) (rowX - offsetX);
            mWindowParams.y = (int) (rowY - offsetY);
            mWindowManager.updateViewLayout(mDragImageView, mWindowParams);
            updateSlidingHorizontalRecyclerView(rowX, rowY);
            findViewPositionInHorizontalRV(rowX, rowY);
            recyclerViewScrollHorizontal((int) rowX, (int) rowY);
            recyclerViewScrollVertical((int) rowX, (int) rowY);
        }
    }

    private void calculateOffset(float x, float y) {
        offsetX = (int) Math.abs(x - mBornLocationX);
        offsetY = (int) Math.abs(y - mBornLocationY);
        confirmOffset = true;
    }

    private void getTargetVerticalRecyclerViewScrollBoundaries() {
        int[] location = new int[2];
        mCurrentVerticalRecycleView.getLocationOnScreen(location);
        upScrollBounce = location[1] + 150;
        downScrollBounce = location[1] + mCurrentVerticalRecycleView.getHeight() - 150;
    }

    private void getTargetHorizontalRecyclerViewScrollBoundaries() {
        leftScrollBounce = 200;
        rightScrollBounce = AttrAboutPhone.screenWidth - 200;
        Log.i("MyTag", "leftScrollBounce " + leftScrollBounce + " rightScrollBounce " + rightScrollBounce);
    }

    private void recyclerViewScrollHorizontal(final int x, final int y) {
        if (mHorizontalScrollTimerTask != null)
            mHorizontalScrollTimerTask.cancel();

        if (x > rightScrollBounce) {
            mHorizontalScrollTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHorizontalRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mHorizontalRecyclerView.scrollBy(HORIZONTAL_STEP, 0);
                            findViewPositionInCurVerticalRV(x, y);
                        }
                    });
                }
            };
            mHorizontalScrollTimer.schedule(mHorizontalScrollTimerTask, 0, HORIZONTAL_SCROLL_PERIOD);
        } else if (x < leftScrollBounce) {
            mHorizontalScrollTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHorizontalRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mHorizontalRecyclerView.scrollBy(-HORIZONTAL_STEP, 0);
                            findViewPositionInCurVerticalRV(x, y);
                        }
                    });
                }
            };
            mHorizontalScrollTimer.schedule(mHorizontalScrollTimerTask, 0, HORIZONTAL_SCROLL_PERIOD);
        }
    }

    private void recyclerViewScrollVertical(final int x, final int y) {
        if (mVerticalScrollTimerTask != null)
            mVerticalScrollTimerTask.cancel();
        if (y > downScrollBounce) {
            mVerticalScrollTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mCurrentVerticalRecycleView.post(new Runnable() {
                        @Override
                        public void run() {
                            mCurrentVerticalRecycleView.scrollBy(0, VERTICAL_STEP);
                            findViewPositionInCurVerticalRV(x, y);
                        }
                    });
                }
            };
            mVerticalScrollTimer.schedule(mVerticalScrollTimerTask, 0, VERTICAL_SCROLL_PERIOD);
        } else if (y < upScrollBounce) {
            mVerticalScrollTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mCurrentVerticalRecycleView.post(new Runnable() {
                        @Override
                        public void run() {
                            mCurrentVerticalRecycleView.scrollBy(0, -VERTICAL_STEP);
                            findViewPositionInCurVerticalRV(x, y);
                        }
                    });
                }
            };
            mVerticalScrollTimer.schedule(mVerticalScrollTimerTask, 0, VERTICAL_SCROLL_PERIOD);
        }
    }


    private int getHorizontalCurrentPosition(float rowX, float rowY) {
        int[] location = new int[2];
        mHorizontalRecyclerView.getLocationOnScreen(location);
        float x = rowX - location[0];
        float y = rowY - location[1];
        View child = mHorizontalRecyclerView.findChildViewUnder(x, y);
        if (child != null) {
            return mHorizontalRecyclerView.getChildAdapterPosition(child);
        }
        return mPagerPosition;
    }
    //endregion
}
