package com.time.cat.dragboardview.callback;

import com.time.cat.dragboardview.model.DragItem;

public interface DragVerticalAdapter {

    void onDrag(int position);
    void onDrop(int page, int position, DragItem tag);
    void onDragOut();
    void onDragIn(int position, DragItem tag);
    void updateDragItemVisibility(int position);
}
