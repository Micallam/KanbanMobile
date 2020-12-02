package com.example.kanbanmobile.db;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kanbanmobile.EditActivity;
import com.example.kanbanmobile.LoginActivity;
import com.example.kanbanmobile.adapters.EventsAdapter;
import com.example.kanbanmobile.adapters.UsersAdapter;
import com.example.kanbanmobile.enums.UserType;
import com.example.kanbanmobile.models.Event;
import com.example.kanbanmobile.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DatabaseHelper {
    final String URL_LOGIN = "http://gajda-adrian.ehost.pl/scripts/login.php";
    final String URL_REGISTER = "http://gajda-adrian.ehost.pl/scripts/register.php";
    final String URL_EDIT = "http://gajda-adrian.ehost.pl/scripts/edit.php";
    final String URL_DELETE = "http://gajda-adrian.ehost.pl/scripts/delete.php";
    final String URL_GET_USERS = "http://gajda-adrian.ehost.pl/scripts/getUsers.php";
    final String URL_ADD_EVENT = "http://gajda-adrian.ehost.pl/scripts/addEvent.php";
    final String URL_GET_EVENTS = "http://gajda-adrian.ehost.pl/scripts/getEvents.php";
    final String URL_DELETE_EVENT = "http://gajda-adrian.ehost.pl/scripts/deleteEvent.php";
    static String secretKey = "mfryy46ABm";
    static String salt = "Hx4wWgDU40";
    private Context context;
    ProgressBar progressBar;

    public DatabaseHelper(Context context, @Nullable ProgressBar progressBar){
        this.context = context;
        this.progressBar = progressBar;
    }

    public void Login(final String login, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Toast.makeText(context, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();

                                    if (progressBar != null)
                                        progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(context, EditActivity.class); //wywołać activity po zalogowaniu
                                    intent.putExtra("login", login);
                                    context.startActivity(intent);
                                }

                            } else {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(context, "Nieprawidłowy login lub hasło!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "Błąd! " +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd! " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", encrypt(password));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void Register(final String login, final String password, final String userType, final Class activityToRedirect){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Zarejestrowano pomyślnie!", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, activityToRedirect));
                            } else {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Podany login jest zajęty!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd rejestracji! " + e.toString(), Toast.LENGTH_SHORT).show();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd rejestracji! " + error.toString(), Toast.LENGTH_SHORT).show();

                        if (progressBar != null)
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", encrypt(password));
                params.put("type", userType);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void loadUsers(final ListView lvUsers) {
        final ArrayList<User> arrayOfUsers = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for(int i=0; i<array.length(); i++) {
                        JSONObject usersJSON = array.getJSONObject(i);

                        arrayOfUsers.add(new User(
                                usersJSON.getString("login").trim(),
                                UserType.fromInteger(Integer.parseInt(usersJSON.getString("type").trim()))
                        ));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                UsersAdapter adapter = new UsersAdapter(context, arrayOfUsers);
                lvUsers.setAdapter(adapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                })
        ;

        Volley.newRequestQueue(context).add(stringRequest);
    }

    public static String encrypt(String strToEncrypt)
    {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            String result = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return result;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try
        {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public void Edit(final String login, final String oldPassword, final String newPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Zaktualizowano pomyślnie!", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, LoginActivity.class));
                            } else {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Nieprawidłowe hasło!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd aktualizacji! " + e.toString(), Toast.LENGTH_SHORT).show();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd aktualizacji! " + error.toString(), Toast.LENGTH_SHORT).show();

                        if (progressBar != null)
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("old_password", encrypt(oldPassword));
                params.put("new_password", encrypt(newPassword));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void Delete(final String login, final Class activityToRedirect) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Konto usunięte pomyślnie!", Toast.LENGTH_SHORT).show();

                                if (activityToRedirect != null)
                                    context.startActivity(new Intent(context, activityToRedirect));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd usuwania konta! " + e.toString(), Toast.LENGTH_SHORT).show();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd usuwania konta! " + error.toString(), Toast.LENGTH_SHORT).show();

                        if (progressBar != null)
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void addEvent(final Event event, final Class activityToRedirect){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Wydarzenie dodano pomyślnie!", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, activityToRedirect));
                            } else {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Błąd dodawania!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd dodawania! " + e.toString(), Toast.LENGTH_SHORT).show();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd dodawania! " + error.toString(), Toast.LENGTH_SHORT).show();

                        if (progressBar != null)
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", event.getName());
                params.put("description", event.getDescription());
                params.put("eventDateTime", event.getEventDateTimeString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void deleteEvent(final Event event) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                if (progressBar != null)
                                    progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(context, "Wydarzenie usunięte pomyślnie!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd usuwania wydarzenia! " + e.toString(), Toast.LENGTH_SHORT).show();

                            if (progressBar != null)
                                progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd usuwania wydarzenia! " + error.toString(), Toast.LENGTH_SHORT).show();

                        if (progressBar != null)
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", event.getName());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void loadEvents(final ListView lvEvents) {
        final ArrayList<Event> arrayOfEvents = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_EVENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for(int i=0; i<array.length(); i++) {
                        JSONObject eventsJSON = array.getJSONObject(i);

                        arrayOfEvents.add(new Event(
                                eventsJSON.getString("name").trim(),
                                eventsJSON.getString("description").trim(),
                                eventsJSON.getString("eventDateTime")
                        ));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                EventsAdapter adapter = new EventsAdapter(context, arrayOfEvents);
                lvEvents.setAdapter(adapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                ;

        Volley.newRequestQueue(context).add(stringRequest);
    }
}
