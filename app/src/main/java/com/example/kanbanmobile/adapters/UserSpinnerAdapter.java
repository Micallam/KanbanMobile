package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanbanmobile.R;
import com.example.kanbanmobile.models.User;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserSpinnerAdapter extends RecyclerView.Adapter<UserSpinnerAdapter.EquipmentViewHolder> {
    private Context ctx;
    private List<User> userList;

    public UserSpinnerAdapter(Context ctx, List<User> userList) {
        this.ctx = ctx;
        this.userList = userList;
    }

    @Override
    public UserSpinnerAdapter.EquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item_user_spinner, null);
        return new UserSpinnerAdapter.EquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserSpinnerAdapter.EquipmentViewHolder holder, int position) {
        User userModel = userList.get(position);

        holder.textViewEquipmentName.setText(userModel.getLogin());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class EquipmentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEquipmentName;

        public EquipmentViewHolder(View itemView) {
            super(itemView);

            textViewEquipmentName = itemView.findViewById(R.id.user);
        }
    }
}
