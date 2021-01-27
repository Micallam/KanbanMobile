package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kanbanmobile.shared.SharedPreferenceConfig;

public class AdminActivity extends AppCompatActivity {

    Button btnAdminPanel;
    Button btnCalendar;
    Button btnBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

        btnAdminPanel = findViewById(R.id.btn_admin_panel_start);
        btnCalendar = findViewById(R.id.btn_calendar);
        btnBoard = findViewById(R.id.btn_board);

        btnAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,
                        AdminPanelActivity.class);
                startActivity(i);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,
                        CalendarActivity.class);
                startActivity(i);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,
                        BoardActivity.class);
                startActivity(i);
            }
        });
    }
}