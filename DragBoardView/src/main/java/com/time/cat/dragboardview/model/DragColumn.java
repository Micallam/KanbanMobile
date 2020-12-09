package com.time.cat.dragboardview.model;

import androidx.annotation.IntRange;

import java.util.List;

public interface DragColumn {
    List<? extends DragItem> getItemList();

    @IntRange(from = 0)
    int getColumnIndex();

    void setColumnIndex(@IntRange(from = 0) int columnIndexInHorizontalRecycleView);
}
