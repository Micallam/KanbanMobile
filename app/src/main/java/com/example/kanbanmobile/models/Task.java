package com.example.kanbanmobile.models;

import com.example.kanbanmobile.enums.TaskStatus;
import com.example.kanbanmobile.utils.AndroidUtil;
import com.time.cat.dragboardview.model.DragItem;

import java.time.LocalDateTime;

public class Task implements DragItem {


    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    private int id;
    private String title;
    private String description;
    private User createdBy;
    private User assignedUser;
    private TaskStatus taskStatus;
    private LocalDateTime createdDateTime;

    public Task(int id, String title, String description, User createdBy, User assignedUser, TaskStatus taskStatus, String createdDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.assignedUser = assignedUser;
        this.taskStatus = taskStatus;
        this.createdDateTime = AndroidUtil.stringToEventDateTime(createdDateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
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
