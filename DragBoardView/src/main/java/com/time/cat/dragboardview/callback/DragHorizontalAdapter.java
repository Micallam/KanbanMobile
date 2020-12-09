package com.time.cat.dragboardview.callback;

import com.time.cat.dragboardview.model.DragColumn;

public interface DragHorizontalAdapter {

    void onDrag(int position);
    void onDrop(int page, int position, DragColumn tag);
    void onDragOut();
    void onDragIn(int position, DragColumn tag);
    void updateDragItemVisibility(int position);
}
