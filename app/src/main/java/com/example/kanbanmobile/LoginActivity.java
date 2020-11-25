package com.example.kanbanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kanbanmobile.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText login, password;
    Button btnLogin;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(LoginActivity.this, progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String mLogin = login.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mLogin.isEmpty() && !mPass.isEmpty())
                    databaseHelper.Login(mLogin, mPass);
                else if(mLogin.isEmpty())
                    login.setError("Wpisz login!");
                else if(mPass.isEmpty())
                    password.setError("Wpisz hasło!");
                else {
                    login.setError("Wpisz login!");
                    password.setError("Wpisz hasło!");
                }
           }
        });
    }
}