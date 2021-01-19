package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanbanmobile.R;
import com.example.kanbanmobile.TaskDetailsActivity;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.enums.TaskStatus;
import com.example.kanbanmobile.models.Task;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;
import com.time.cat.dragboardview.adapter.VerticalAdapter;
import com.time.cat.dragboardview.helper.DragHelper;
import com.time.cat.dragboardview.model.DragItem;

public class TaskAdapter extends VerticalAdapter<TaskAdapter.ViewHolder> {

    private OnItemClickListener mListener;

    public TaskAdapter(Context context, DragHelper dragHelper) {
        super(context, dragHelper);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_item, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final Context context, final ViewHolder holder, @NonNull DragItem item, final int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dragItem(holder);
                return true;
            }
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(context);
                sharedPreferenceConfig.setSelectedTaskId(holder.item_id.getText().toString());
                sharedPreferenceConfig.setSelectedTaskTitle(holder.item_title.getText().toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(context,null);
                databaseHelper.getTaskDetails(sharedPreferenceConfig.getSelectedTaskId());
            }
        });*/
        holder.item_title.setText(((Task) item).getTitle());
        int itemIdInt = ((Task) item).getId();
        String itemIdString = String.valueOf(itemIdInt);
        holder.item_id.setText(itemIdString);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title;
        TextView item_id;

        public ViewHolder(View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_id = itemView.findViewById(R.id.item_id);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onDrop(int page, int position, DragItem tag) {
        super.onDrop(page, position, tag);

        Task task = (Task) tag;

        if (task.getTaskStatus() != TaskStatus.fromInteger(page)) {
            task.setTaskStatus(TaskStatus.fromInteger(page));

            DatabaseHelper databaseHelper = new DatabaseHelper(mContext, null);
            databaseHelper.updateTaskStatus(task);
        }
    }
}
