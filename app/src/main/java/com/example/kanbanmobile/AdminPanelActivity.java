package com.example.kanbanmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kanbanmobile.db.DatabaseHelper;
import com.example.kanbanmobile.shared.SharedPreferenceConfig;

public class AdminPanelActivity extends AppCompatActivity {

    Button btnAddUser;
    Button btnShowList;
    ListView lvUsers;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

        lvUsers = findViewById(R.id.user_listView);

        final DatabaseHelper databaseHelper = new DatabaseHelper(AdminPanelActivity.this, null);

        btnAddUser = findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPanelActivity.this,
                        AddUserActivity.class);
                startActivity(i);
            }
        });

        btnShowList = findViewById(R.id.btn_showlist);
        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvUsers.getVisibility() == View.VISIBLE) {
                    btnShowList.setText("Lista użytkowników");
                    lvUsers.setVisibility(View.INVISIBLE);
                } else {
                    btnShowList.setText("Ukryj");
                    databaseHelper.loadUsers(lvUsers);
                    lvUsers.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
