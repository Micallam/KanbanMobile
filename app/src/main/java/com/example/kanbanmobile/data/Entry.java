package com.example.kanbanmobile.data;

import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;

import java.util.List;

public class Entry implements DragColumn {
    private final String id;
    private final String name;
    private final List<DragItem> itemList;

    public Entry(String id, String name, List<DragItem> items) {
        this.id = id;
        this.name = name;
        this.itemList = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DragItem> getItemList() {
        return itemList;
    }

    @Override
    public int getColumnIndex() {
        return 0;
    }

    @Override
    public void setColumnIndex(int columnIndexInHorizontalRecycleView) {

    }
}
