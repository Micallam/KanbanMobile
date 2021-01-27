package com.time.cat.dragboardview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.time.cat.dragboardview.adapter.HorizontalAdapter;
import com.time.cat.dragboardview.helper.DragHelper;

import org.jetbrains.annotations.Nullable;

public class DragBoardView extends DragLayout {
    private PagerRecyclerView mRecyclerView;
    private HorizontalAdapter mAdapter;
    private DragLayout mLayoutMain;
    private DragHelper mDragHelper;

    public DragBoardView(Context context) {
        super(context);
        init(context);
    }

    public DragBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DragBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_drag_board, this);
        mLayoutMain = (DragLayout) findViewById(R.id.layout_main);
        mRecyclerView = (PagerRecyclerView) findViewById(R.id.rv_lists);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFlingFactor(0.1f);
        mRecyclerView.addOnPageChangedListener(mOnPagerChangedListener);
        mRecyclerView.addOnLayoutChangeListener(mOnLayoutChangedListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mDragHelper = new DragHelper(context);
        mDragHelper.bindHorizontalRecyclerView(mRecyclerView);
        mLayoutMain.setDragHelper(mDragHelper);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int childCount = mRecyclerView.getChildCount();
            int width = mRecyclerView.getChildAt(0).getWidth();
            int padding = (mRecyclerView.getWidth() - width) / 2;

            for (int j = 0; j < childCount; j++) {
                View v = recyclerView.getChildAt(j);

                float rate = 0;
                if (v.getLeft() <= padding) {
                    if (v.getLeft() >= padding - v.getWidth()) {
                        rate = (padding - v.getLeft()) * 1f / v.getWidth();
                    } else {
                        rate = 1;
                    }
                    v.setScaleX(1 - rate * 0.1f);

                } else {
                    if (v.getLeft() <= recyclerView.getWidth() - padding) {
                        rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                    }
                    v.setScaleX(0.9f + rate * 0.1f);
                }
            }
        }
    };
    private View.OnLayoutChangeListener mOnLayoutChangedListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        }
    };
    private PagerRecyclerView.OnPageChangedListener mOnPagerChangedListener = new PagerRecyclerView.OnPageChangedListener() {
        @Override
        public void OnPageChanged(int oldPosition, int newPosition) {

        }
    };

    public void setHorizontalAdapter(@NonNull HorizontalAdapter horizontalAdapter) {
        this.mAdapter = horizontalAdapter;
        mAdapter.setDragHelper(mDragHelper);
        mRecyclerView.setAdapter(mAdapter);
    }

    public DragHelper getDragHelper() {
        return mDragHelper;
    }

    public PagerRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public HorizontalAdapter getAdapter() {
        return mAdapter;
    }
}
