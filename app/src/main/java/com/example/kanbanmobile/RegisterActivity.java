package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.kanbanmobile.db.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText login, password;
    Button btnRegister;
    ProgressBar progressBar;
    RadioButton typeAdmin, typeUser;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btn_regist);
        progressBar = findViewById(R.id.progress_bar);
        typeUser = findViewById(R.id.type_user);
        typeAdmin = findViewById(R.id.type_admin);

        progressBar.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(RegisterActivity.this, progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String getLogin = login.getText().toString().trim();
                String getPassword = password.getText().toString().trim();

                if (!getLogin.isEmpty() && !getPassword.isEmpty()) {
                    String userType = checkType();
                    databaseHelper.Register(getLogin, getPassword, userType);
                } else if(getLogin.isEmpty()) {
                    login.setError("Wprowadź login!");
                }
                else if(getPassword.isEmpty()) {
                    password.setError("Wprowadź hasło!");
                }
                else {
                    login.setError("Wprowadź login!");
                    password.setError("Wprowadź hasło!");
                }

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String checkType() {
        if(typeAdmin.isChecked())
            return "1";
        else
            return "0";
    }
}