package com.time.cat.dragboardview.callback;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface DragHorizontalViewHolder {

    RecyclerView getRecyclerView();

    void findViewForContent(View itemView);
}
