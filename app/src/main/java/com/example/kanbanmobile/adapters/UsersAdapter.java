package com.example.kanbanmobile.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kanbanmobile.AdminPanelActivity;
import com.example.kanbanmobile.R;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.User;
import com.example.kanbanmobile.utils.ConfirmDeletionDialog;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        TextView tvLogin = convertView.findViewById(R.id.login);
        TextView tvUserType = convertView.findViewById(R.id.userType);

        tvLogin.setText(user.getLogin());
        tvUserType.setText(user.getUserType().toString());

        Button btnDeleteUser = convertView.findViewById(R.id.deleteUser);
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeletionDialog dialog = new ConfirmDeletionDialog(getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UsersAdapter.super.remove(user);

                        new DatabaseHelper(getContext(), null)
                                .Delete(user.getLogin(), null);
                    }
                });

                dialog.show(
                        ((AdminPanelActivity)getContext()).getSupportFragmentManager(),
                        "ConfirmDeleteUserDialog"
                );
            }
        });

        return convertView;
    }
}
