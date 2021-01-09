package com.example.kanbanmobile.data;

import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;

import java.util.List;

public class StatusDragColumn implements DragColumn {
    private final String id;
    private final String name;
    private final List<DragItem> taskList;

    public StatusDragColumn(String id, String name, List<DragItem> taskList) {
        this.id = id;
        this.name = name;
        this.taskList = taskList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DragItem> getTaskList() {
        return taskList;
    }

    @Override
    public int getColumnIndex() {
        return 0;
    }

    @Override
    public void setColumnIndex(int columnIndexInHorizontalRecycleView) {

    }
}
