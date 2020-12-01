package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.models.User;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {

    EditText title, description;
    Spinner spinnerAssignedUser;
    Button btnAddTask;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private ArrayList<String> users = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        spinnerAssignedUser = findViewById(R.id.user_spinner);
        btnAddTask = findViewById(R.id.btn_add_task);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(this, progressBar);

        databaseHelper.getUsersToSpinner(users, spinnerAssignedUser);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String getTitle = title.getText().toString().trim();
                String getDescription = description.getText().toString().trim();
                String getAssignedUser = spinnerAssignedUser.getSelectedItem().toString();

                if (!getTitle.isEmpty()) {
                    databaseHelper.addTask(getTitle, getDescription, getAssignedUser, EditActivity.class);
                } else {
                    title.setError("Wprowadź tytuł!");
                }
            }
        });
    }
}