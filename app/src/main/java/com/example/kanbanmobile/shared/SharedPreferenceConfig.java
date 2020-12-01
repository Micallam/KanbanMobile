package com.example.kanbanmobile.shared;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.kanbanmobile.LoginActivity;
import com.example.kanbanmobile.MainActivity;
import com.example.kanbanmobile.R;

import static java.security.AccessController.getContext;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_shared_preference), Context.MODE_PRIVATE);
    }

    public void loginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_shared_preference), status);
        editor.commit();
    }

    public void loggedUser(String login) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", login);
        editor.commit();
    }

    public boolean readLoginStatus() {
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_shared_preference), false);
        return status;
    }

    public String getLoggedUser() {
        String loggedUser = "";
        loggedUser = sharedPreferences.getString("login", "");
        return loggedUser;
    }

    public void checkIfLogged(Context ctx) {
        if(!readLoginStatus()) {
            ctx.startActivity(new Intent(ctx, MainActivity.class));
            ((Activity) ctx).finish();
        } else {}
    }
}
