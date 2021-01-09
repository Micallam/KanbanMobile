package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.kanbanmobile.R;
import com.example.kanbanmobile.data.StatusDragColumn;
import com.example.kanbanmobile.data.TaskDragItem;
import com.example.kanbanmobile.enums.TaskStatus;
import com.time.cat.dragboardview.adapter.HorizontalAdapter;
import com.time.cat.dragboardview.model.DragColumn;
import com.time.cat.dragboardview.model.DragItem;

import java.util.ArrayList;
import java.util.List;

public class ColumnAdapter extends HorizontalAdapter<ColumnAdapter.ViewHolder> {

    public ColumnAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean needFooter() {
        return true;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.recyclerview_item_entry;
    }

    @Override
    public int getFooterLayoutRes() {
        return R.layout.recyclerview_footer_addlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(View parent, int viewType) {
        return new ViewHolder(parent, viewType);
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

        final ItemAdapter itemAdapter = new ItemAdapter(mContext, dragHelper);
        itemAdapter.setData(itemList);
        holder.rv_item.setAdapter(itemAdapter);
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
                                // add new Item instantly
                                itemList.add(new TaskDragItem(
                                        "entry " + " item id ",
                                        "item name : new Item",
                                        "info : new Item"));
                                itemAdapter.notifyItemInserted(itemAdapter.getItemCount() - 1);
                                // then add new Item to your database in io thread
                                // then view will auto-refresh
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

    @Override
    public void onBindFooterViewHolder(final ViewHolder holder, int position) {
//        holder.add_subPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.add_subPlan.setVisibility(View.GONE);
//                holder.edit_sub_plan.setVisibility(View.VISIBLE);
//            }
//        });
//        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.add_subPlan.setVisibility(View.VISIBLE);
//                holder.edit_sub_plan.setVisibility(View.GONE);
//            }
//        });
//        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = holder.editText.getText().toString();
//                if (!TextUtils.isEmpty(name)) {
//                    appendNewColumn(new StatusDragColumn(
//                            "entry id " + name,
//                            "name : new entry",
//                            new ArrayList<DragItem>()));
//                }
//            }
//        });
    }

    public class ViewHolder extends HorizontalAdapter.ViewHolder {

        RelativeLayout col_content_container;
        ImageView title_icon;
        TextView tv_title;
        RecyclerView rv_item;
        RelativeLayout add_task;

//        RelativeLayout add_subPlan;
//        RelativeLayout edit_sub_plan;
//        Button btn_cancel;
//        Button btn_ok;
//        EditText editText;

        public ViewHolder(View convertView, int itemType) {
            super(convertView, itemType);
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

        @Override
        public void findViewForFooter(View convertView) {
//            add_subPlan = convertView.findViewById(R.id.add_sub_plan);
//            edit_sub_plan = convertView.findViewById(R.id.edit_sub_plan);
//            btn_cancel = convertView.findViewById(R.id.add_cancel);
//            btn_ok = convertView.findViewById(R.id.add_ok);
//            editText = convertView.findViewById(R.id.add_et);
        }
    }
}


