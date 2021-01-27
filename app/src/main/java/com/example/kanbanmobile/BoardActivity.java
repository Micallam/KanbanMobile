package com.example.kanbanmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kanbanmobile.adapters.ColumnAdapter;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.Task;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;
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

        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

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
    }

}
