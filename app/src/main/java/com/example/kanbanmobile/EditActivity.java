package com.example.kanbanmobile;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kanbanmobile.db.DatabaseHelper;

public class EditActivity extends AppCompatActivity {

    EditText oldPassword, newPassword;
    Button btnEdit, btnDelete;
    ProgressBar progressBar;
    DatabaseHelper databaseHelper;
    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        progressBar = findViewById(R.id.progress_bar);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);

        initButtonsClick();

        Intent intent = getIntent();
        login = intent.getStringExtra("login");

        progressBar.setVisibility(View.INVISIBLE);

        databaseHelper = new DatabaseHelper(EditActivity.this, progressBar);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String getOldPassword = oldPassword.getText().toString().trim();
                String getNewPassword = newPassword.getText().toString().trim();

                if (!getOldPassword.isEmpty() && !getNewPassword.isEmpty()) {
                    databaseHelper.Edit(login, getOldPassword, getNewPassword);
                } else if(getOldPassword.isEmpty()) {
                    oldPassword.setError("Wprowadź stare hasło!");
                }
                else if(getNewPassword.isEmpty()) {
                    newPassword.setError("Wprowadź nowe hasło!");
                }
                else {
                    oldPassword.setError("Wprowadź stare hasło!");
                    newPassword.setError("Wprowadź nowe hasło!");
                }

                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        /*btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditActivity.this);
                dialogBuilder.setTitle("Usunięcie konta");
                dialogBuilder.setMessage("Czy na pewno chcesz usunąć konto?");
                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        databaseHelper.Delete(login);
                    }
                });
                dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                progressBar.setVisibility(View.INVISIBLE);
            }
        });*/
    }

    private void initButtonsClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(2);
            }
        };
        btnDelete.setOnClickListener(listener);
    }

    protected Dialog onCreateDialog(int id) {
        return createAlertDialogWithButtons();
    }

    private Dialog createAlertDialogWithButtons() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Usunięcie konta");
        dialogBuilder.setMessage("Chcesz usunąć konto?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                databaseHelper.Delete(login, LoginActivity.class);
            }
        });
        dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        return dialogBuilder.create();
    }
}