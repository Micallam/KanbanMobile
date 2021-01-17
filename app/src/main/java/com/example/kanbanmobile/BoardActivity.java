package com.example.kanbanmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kanbanmobile.adapters.ColumnAdapter;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.Task;
import com.time.cat.dragboardview.DragBoardView;
import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.utils.AttrAboutPhone;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private ColumnAdapter mAdapter;
    DragBoardView dragBoardView;
    private List<DragColumn> mColumnList = new ArrayList<>();
    private List<Task> mTaskList = new ArrayList<>();

    final DatabaseHelper databaseHelper = new DatabaseHelper(BoardActivity.this, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        dragBoardView = findViewById(R.id.drag_board);
        mAdapter = new ColumnAdapter(this);
        mAdapter.setData(mColumnList);
        dragBoardView.setHorizontalAdapter(mAdapter);

        getDataAndRefreshView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        AttrAboutPhone.saveAttr(this);
        AttrAboutPhone.initScreen(this);
        super.onWindowFocusChanged(hasFocus);
    }

    private void getDataAndRefreshView() {
        databaseHelper.loadTask(mAdapter, mColumnList);

//        for (int i = 0; i < 3; i++) {
//            List<DragItem> itemList = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                itemList.add(new TaskDragItem("entry " + i + " item id " + j, "item name " + j, "info " + j));
//            }
//
//            switch (i)
//            {
//                case 0:
//                    mColumnList.add(new StatusDragColumn("Nowe", "Nowe", itemList));
//                    break;
//                case 1:
//                    mColumnList.add(new StatusDragColumn("W trakcie", "W trakcie", itemList));
//                    break;
//                case 2:
//                    mColumnList.add(new StatusDragColumn("Skończone", "Skończone", itemList));
//                    break;
//                default:
//                    mColumnList.add(new StatusDragColumn("entry id " + i, "name " + i, itemList));
//
//            }
//        }
//        mAdapter.notifyDataSetChanged();
    }

}
