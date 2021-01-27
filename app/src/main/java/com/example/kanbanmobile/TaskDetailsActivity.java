package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kanbanmobile.adapters.UsersAdapter;
import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.Task;
import com.example.kanbanmobile.models.User;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;
import com.example.kanbanmobile.utils.ConfirmDeletionDialog;

import java.util.ArrayList;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView itemId;
    EditText itemTitle, itemDescription;
    Spinner spinnerAssignedUser;
    Button btnEdit, btnDelete;
    private ArrayList<String> users = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

        itemId = findViewById(R.id.item_id);
        itemTitle = findViewById(R.id.item_title);
        itemDescription = findViewById(R.id.item_description);
        spinnerAssignedUser = findViewById(R.id.user_spinner);

        itemId.setText(sharedPreferenceConfig.getSelectedTaskId());
        itemTitle.setText(sharedPreferenceConfig.getSelectedTaskTitle());
        itemDescription.setText(sharedPreferenceConfig.getSelectedTaskDescription());

        btnDelete = findViewById(R.id.button_task_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeletionDialog dialog = new ConfirmDeletionDialog(TaskDetailsActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int taskId = Integer.parseInt(itemId.getText().toString().trim());

                        new DatabaseHelper(TaskDetailsActivity.this, null)
                                .deleteTask(taskId, BoardActivity.class);
                    }
                });

                dialog.show(
                        TaskDetailsActivity.this.getSupportFragmentManager(),
                        "ConfirmDeleteTaskDialog"
                );
            }
        });

        btnEdit = findViewById(R.id.button_task_save);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(
                        Integer.parseInt(itemId.getText().toString().trim()),
                        itemTitle.getText().toString(),
                        itemDescription.getText().toString(),
                        new User(spinnerAssignedUser.getSelectedItem().toString())
                );

                new DatabaseHelper(TaskDetailsActivity.this, null)
                        .updateTask(task, BoardActivity.class);
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(this, null);
        databaseHelper.getUsersToSpinner(users, spinnerAssignedUser);
    }
}