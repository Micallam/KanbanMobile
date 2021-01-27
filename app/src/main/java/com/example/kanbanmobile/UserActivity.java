package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kanbanmobile.shared.SharedPreferenceConfig;

public class UserActivity extends AppCompatActivity {

    Button btnCalendar;
    Button btnBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        sharedPreferenceConfig.checkIfLogged(this);

        btnCalendar = findViewById(R.id.btn_calendar);
        btnBoard = findViewById(R.id.btn_board);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this,
                        CalendarActivity.class);
                startActivity(i);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserActivity.this,
                        BoardActivity.class);
                startActivity(i);
            }
        });
    }
}