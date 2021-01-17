package com.time.cat.dragboardview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;

import com.time.cat.dragboardview.callback.DragHorizontalAdapter;
import com.time.cat.dragboardview.callback.DragHorizontalViewHolder;
import com.time.cat.dragboardview.helper.DragHelper;
import com.time.cat.dragboardview.model.DragColumn;

import java.util.Collections;
import java.util.List;

import static com.time.cat.dragboardview.helper.DragHelper.TYPE_CONTENT;

public abstract class HorizontalAdapter<VH extends HorizontalAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements DragHorizontalAdapter {

    protected Context mContext;
    private List<DragColumn> mData;
    protected DragHelper dragHelper;

    private int mDragPosition;
    private boolean mHideDragItem;

    public HorizontalAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext)
                .inflate(getContentLayoutRes(), parent, false);
        return onCreateViewHolder(convertView, TYPE_CONTENT);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_CONTENT:
                final DragColumn dragColumn = mData.get(position);
                if (position == mDragPosition && mHideDragItem) {
                    holder.itemView.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    holder.itemView.setVisibility(View.VISIBLE);
                }
                holder.itemView.setTag(dragColumn);

                onBindContentViewHolder(holder, dragColumn, position);
                break;
            default:
                break;
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CONTENT;
    }

    @Override
    public void onDrag(int position) {
        mDragPosition = position;
        mHideDragItem = true;
        notifyItemChanged(position);
    }

    @Override
    public void onDrop(int page, int position, DragColumn tag) {
        mHideDragItem = false;
        notifyDataSetChanged();
    }

    @Override
    public void onDragOut() {
        if (mDragPosition >= 0 && mDragPosition < mData.size()) {
            mData.remove(mDragPosition);
            notifyDataSetChanged();
            mDragPosition = -1;
        }
    }

    @Override
    public void onDragIn(int position, DragColumn dragColumnObject) {
        if (position > mData.size()) {
            position = mData.size();
        }
        mData.add(position, dragColumnObject);
        notifyItemInserted(position);
        mDragPosition = position;
        mHideDragItem = true;
    }

    @Override
    public void updateDragItemVisibility(int position) {
        if (mDragPosition >= 0 && mDragPosition < mData.size() && position < mData.size() && mDragPosition != position) {
            if (Math.abs(mDragPosition - position) == 1) {
                notifyItemChanged(mDragPosition);
                Collections.swap(mData, mDragPosition, position);
                mDragPosition = position;
                notifyItemChanged(position);
            } else {
                notifyItemChanged(mDragPosition);
                if (mDragPosition > position) {
                    for (int i = mDragPosition; i > position; i--) {
                        Collections.swap(mData, i, i - 1);
                        notifyItemChanged(i);
                    }
                } else {
                    for (int i = mDragPosition; i < position; i++) {
                        Collections.swap(mData, i, i + 1);
                        notifyItemChanged(i);
                    }
                }
                mDragPosition = position;
                notifyItemChanged(position);
            }
        }
    }

    public List<DragColumn> getData() {
        return mData;
    }

    public void setData(List<DragColumn> mData) {
        this.mData = mData;
    }

    public void appendNewColumn(DragColumn dragColumn) {
        getData().add(dragColumn);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addNewColumn(@IntRange(from = 0) int position, DragColumn dragColumn) {
        getData().add(position, dragColumn);
        notifyItemInserted(position);
    }

    public void setDragHelper(DragHelper dragHelper) {
        this.dragHelper = dragHelper;
    }

    public void dragCol(View columnView, int position) {
        if (dragHelper != null) {
            dragHelper.dragCol(columnView, position);
        }
    }

    public void dragCol(VH holder) {
        dragCol(holder.itemView, holder.getAdapterPosition());
    }

    public abstract int getContentLayoutRes();

    public abstract VH onCreateViewHolder(View parent, int viewType);

    public abstract void onBindContentViewHolder(final VH holder, DragColumn dragColumn, int position);

    public abstract class ViewHolder extends RecyclerView.ViewHolder implements DragHorizontalViewHolder {

        public ViewHolder(View convertView) {
            super(convertView);

            findViewForContent(convertView);
        }

        @Override
        public abstract RecyclerView getRecyclerView();

        @Override
        public abstract void findViewForContent(View convertView);
    }
}
