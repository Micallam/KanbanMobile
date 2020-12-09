package com.example.kanbanmobile;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kanbanmobile.adapters.ColumnAdapter;
import com.example.kanbanmobile.data.Entry;
import com.example.kanbanmobile.data.Item;
import com.time.cat.dragboardview.DragBoardView;
import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;
import com.time.cat.dragboardview.utils.AttrAboutPhone;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private ColumnAdapter mAdapter;
    DragBoardView dragBoardView;
    private List<DragColumn> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        dragBoardView = findViewById(R.id.layout_main);
        mAdapter = new ColumnAdapter(this);
        mAdapter.setData(mData);
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
        for (int i = 0; i < 3; i++) {
            List<DragItem> itemList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                itemList.add(new Item("entry " + i + " item id " + j, "item name " + j, "info " + j));
            }

            switch (i)
            {
                case 0:
                    mData.add(new Entry("Nowe", "Nowe", itemList));
                    break;
                case 1:
                    mData.add(new Entry("W trakcie", "W trakcie", itemList));
                    break;
                case 2:
                    mData.add(new Entry("Skończone", "Skończone", itemList));
                    break;
                default:
                    mData.add(new Entry("entry id " + i, "name " + i, itemList));

            }
        }
        mAdapter.notifyDataSetChanged();
    }

}
