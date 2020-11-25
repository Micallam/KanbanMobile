package com.example.kanbanmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;
    Button btnAdminPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login_start);
        btnRegister = findViewById(R.id.btn_register_start);
        btnAdminPanel = findViewById(R.id.btn_admin_panel_start);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(i);
            }
        });

        btnAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        AdminPanelActivity.class);
                startActivity(i);
            }
        });
    }
}

