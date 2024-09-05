package com.example.e_hairsalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btn_login;
    TextView signupTv, errorTv;
    EditText username, password;
    SessionManager sessionManager;

    String SITE_URL = "http://192.168.191.218/E-hairsalon/";  //Localhost

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setSite_Url(SITE_URL);
        change_language();

        signupTv = findViewById(R.id.signupTv);
        btn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        errorTv = findViewById(R.id.errorTv);

        if (sessionManager.getLogin()) {
            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);

//            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("SITE_URL", SITE_URL); //localhost
            startActivity(intent);
            finish();
        }


        btn_login.setOnClickListener(view -> {
            loginP();
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
        });
        signupTv.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            startActivity(intent);
        });
    }

    private void loginP() {
        String u_name, pass;
        u_name = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        if (!u_name.equals("") && !pass.equals("")) {
            Log.e("TAG", "userLogin: "+u_name+" "+pass+" ");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, sessionManager.getSite_Url() + "user_login.php", response -> {
                try {
                    Log.e("TAG", "In userLogin: "+u_name+" "+pass+" "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray jsonArray1 = jsonObject.getJSONArray("status");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        String code = jsonObject1.getString("code");
                        switch (code) {
                            case "-1":
                                Toast.makeText(Login.this, "Something went wrong error", Toast.LENGTH_SHORT).show();
                                break;
                            case "0":
                                errorTv.setText("Username or Password Wrong");
                                errorTv.setVisibility(View.VISIBLE);
                                break;
                            case "1":

//                                SharedPref.setUserType(getApplicationContext(), SharedPref.PERSONAL);
                                personalUserValues(jsonArray);
                                Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                                finish();

                                break;
                            case "2":
                                errorTv.setText("Password is wrong");
                                errorTv.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                } catch (JSONException e) {
                    Log.e("TAG", "userLogin: error 1 "+e.getMessage()+" ");
                    throw new RuntimeException(e);
                }
            }, error -> {
                Log.e("TAG", "userLogin: error "+error.getMessage()+" ");
            }) {
                @NonNull
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", u_name);
                    params.put("password", pass);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            errorTv.setText("All fields are required");
            errorTv.setVisibility(View.VISIBLE);
        }
    }

    private void personalUserValues(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String username = jsonObject.getString("username");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email_id");
            String phone = jsonObject.getString("mobile_no");
            sessionManager.setLogin(true);
            sessionManager.setUsername(username);
            sessionManager.setname(name);
            sessionManager.setUser_ID(id);
            sessionManager.setmobile_no(phone);
            sessionManager.setemail(email);
            sessionManager.setSite_Url(SITE_URL);

        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void change_language() {
        String lang = sessionManager.getApp_language().toString();
//        Toast.makeText(Login.this, "lang " + lang, Toast.LENGTH_SHORT).show();
        setLocale(lang);
//        recreate();
//        Toast.makeText(Login.this, "recreate " , Toast.LENGTH_SHORT).show();
    }

    public void change_language_app(View view) {

//        Toast.makeText(Login.this, "change_language ", Toast.LENGTH_SHORT).show();

        final String Languages[] = {"English", "Hindi", "Gujarati"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        mbuilder.setTitle("Choose Language");
        mbuilder.setSingleChoiceItems(Languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("");
                    dialogInterface.dismiss();
                    recreate();
                } else if (i == 1) {
                    setLocale("hi");
                    dialogInterface.dismiss();
                    recreate();
                } else if (i == 2) {
                    setLocale("gu");
                    dialogInterface.dismiss();
                    recreate();
                }
            }
        });
        mbuilder.create();
        mbuilder.show();
    }

    private void setLocale(String Language) {
//        Toast.makeText(Login.this, "setLocale " + Language, Toast.LENGTH_SHORT).show();
        Locale locale = new Locale(Language);
        locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        sessionManager.setApp_language(Language);
    }
}