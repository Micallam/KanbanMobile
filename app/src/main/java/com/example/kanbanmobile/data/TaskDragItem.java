package com.example.kanbanmobile.data;

import com.time.cat.dragboardview.model.DragItem;

public class TaskDragItem implements DragItem {
    private final String itemId;
    private final String itemName;
    private final String info;

    public TaskDragItem(String itemId, String itemName, String info) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.info = info;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public int getColumnIndex() {
        return 0;
    }

    @Override
    public int getItemIndex() {
        return 0;
    }

    @Override
    public void setColumnIndex(int columnIndexInHorizontalRecycleView) {

    }

    @Override
    public void setItemIndex(int itemIndexInVerticalRecycleView) {

    }
}


