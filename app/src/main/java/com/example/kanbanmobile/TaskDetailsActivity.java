package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;

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

        DatabaseHelper databaseHelper = new DatabaseHelper(this, null);
        databaseHelper.getUsersToSpinner(users, spinnerAssignedUser);
    }
}