package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.kanbanmobile.AddTaskActivity;
import com.example.kanbanmobile.R;
import com.example.kanbanmobile.data.StatusDragColumn;
import com.example.kanbanmobile.enums.TaskStatus;
import com.time.cat.dragboardview.adapter.HorizontalAdapter;
import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;

import java.util.List;

public class ColumnAdapter extends HorizontalAdapter<ColumnAdapter.ViewHolder> {

    public ColumnAdapter(Context context) {
        super(context);
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.recyclerview_item_entry;
    }

    @Override
    public ViewHolder onCreateViewHolder(View parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindContentViewHolder(final ViewHolder holder, DragColumn dragColumn, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dragCol(holder);
                return true;
            }
        });

        final StatusDragColumn statusDragColumn = (StatusDragColumn) dragColumn;
        holder.tv_title.setText(statusDragColumn.getName());

        if (!statusDragColumn.getName().equals(TaskStatus.NEW.toString()))
            holder.add_task.setVisibility(View.INVISIBLE);

        final List<DragItem> itemList = statusDragColumn.getTaskList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holder.rv_item.setLayoutManager(layoutManager);

        final TaskAdapter taskAdapter = new TaskAdapter(mContext, dragHelper);
        taskAdapter.setData(itemList);
        holder.rv_item.setAdapter(taskAdapter);
        holder.add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                // plz replace below with your action
                new MaterialDialog.Builder(mContext)
                        .content("Dodaj zadanie")
                        .positiveText("Dodaj")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Bundle bundle = new Bundle();
                                bundle.putString(AddTaskActivity.REDIRECT_TO_KEY, AddTaskActivity.REDIRECT_TO_BOARD);

                                Intent intent = new Intent(getContext(), AddTaskActivity.class);
                                intent.putExtras(bundle);

                                getContext().startActivity(intent);
                            }
                        })
                        .negativeText("Anuluj")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public class ViewHolder extends HorizontalAdapter.ViewHolder {

        RelativeLayout col_content_container;
        ImageView title_icon;
        TextView tv_title;
        RecyclerView rv_item;
        RelativeLayout add_task;

        public ViewHolder(View convertView) {
            super(convertView);
        }

        @Override
        public RecyclerView getRecyclerView() {
            return rv_item;
        }

        @Override
        public void findViewForContent(View convertView) {
            col_content_container = convertView.findViewById(R.id.col_content_container);
            title_icon = convertView.findViewById(R.id.title_icon);
            tv_title = convertView.findViewById(R.id.tv_title);
            rv_item = convertView.findViewById(R.id.rv);
            add_task = convertView.findViewById(R.id.add);
        }
    }

    @Override
    public void onDrag(int position) {
        super.onDrag(position);

        // TODO Zaimplementować edycję statusu
    }
}


