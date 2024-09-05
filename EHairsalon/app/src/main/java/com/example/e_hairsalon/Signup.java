package com.example.e_hairsalon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText txtName, txtPassword, txtmobileno, txtEmail_id, txtUsername;
    TextView msg;
    Button button;
    int record_id;
    String SITE_URL, police_station_id,Notification_Token;
    Boolean email_check = false, mobile_check = false, username_check = false;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();
        SITE_URL = intent.getStringExtra("SITE_URL");

        msg = findViewById(R.id.msg);
        txtName = findViewById(R.id.fullname);
        txtmobileno = findViewById(R.id.mobileno);
        txtEmail_id = findViewById(R.id.email_id);
        txtUsername = findViewById(R.id.username);
        txtPassword = findViewById(R.id.password);

        sessionManager=new SessionManager(getApplicationContext());
        Notification_Token=sessionManager.getNotification_Token();

        txtmobileno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
//                    Toast.makeText(Police_Register_Activity.this, "charSequence " + txtEmail.getText().toString(), Toast.LENGTH_LONG).show();
                    msg.setVisibility(View.GONE);
                    String mobileno = txtmobileno.getText().toString();
                    boolean e = isValidMobile1(mobileno);
                    if (e == true) {
                        search_user_mobile_info();
//                        Toast.makeText(Police_Register_Activity.this, "email_check " + email_check, Toast.LENGTH_LONG).show();

                    } else {
//                        msg.setVisibility(View.VISIBLE);
//                        msg.setText(Html.fromHtml("<b> InValid Email Address. !  </b>"));
                    }
                }
            }
        });

        txtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
//                    Toast.makeText(Police_Register_Activity.this, "charSequence " + txtEmail.getText().toString(), Toast.LENGTH_LONG).show();
                    msg.setVisibility(View.GONE);
                    String username = txtUsername.getText().toString();
                    if (username.equals("")) {
                        msg.setText(Html.fromHtml("<b> Username Is Empty!  </b>"));
                    } else {
                        search_username_info();
//                        Toast.makeText(Police_Register_Activity.this, "email_check " + email_check, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private boolean isValidMobile1(String phone) {
//        return android.util.Patterns.PHONE.matcher(phone).matches();
        String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
        Matcher m = null;

        Pattern r = Pattern.compile(pattern);
        if (!phone.isEmpty()) {
            m = r.matcher(phone);
            if (phone.length() > 10) {
                msg.setVisibility(View.VISIBLE);
                msg.setText(Html.fromHtml("<b> Enter Valid Mobile No !  </b>"));
                return false;
            }
        } else {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Enter Mobile No !  </b>"));
            return false;
        }
        if (m.find()) {
//            msg_edit.setText(Html.fromHtml("<b> Enter Mobile No !  </b>"));
//            Toast.makeText(MainActivity.this, "MATCH", Toast.LENGTH_LONG).show();

            return true;
        } else {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Mobile No Not Valid !  </b>"));
            return false;
        }
    }

    public void search_username_info() {
        String username = txtUsername.getText().toString().trim();
        username_check = true;
        if (username.equals("")) {
//            Toast.makeText(this, "Enter Valid Vehicle No ", Toast.LENGTH_LONG).show();
            msg.setText(Html.fromHtml("<b> Enter Username !  </b>"));
        } else {
            String url="http://192.168.191.218/E-hairsalon/check_username.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
                            if (response.trim().equals("EXIST")) {
//                                Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();

                                username_check = true;
                                msg.setVisibility(View.VISIBLE);
                                msg.setText(Html.fromHtml("<b> Username Already Exist, Use Another Username !  </b>"));
                            } else if (response.trim().equals("NOT_EXIST")){
                                username_check = false;
//                                Log.e("TAG", "response: "+response+" ");
//                                Toast.makeText(Signup.this,"Else PArt "+ response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Signup.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
            requestQueue.add(stringRequest);
        }
    }

    public void search_user_mobile_info() {
        String mobileno = txtmobileno.getText().toString().trim();
        mobile_check = true;
        if (mobileno.equals("")) {
//            Toast.makeText(this, "Enter Valid Vehicle No ", Toast.LENGTH_LONG).show();
            msg.setText(Html.fromHtml("<b> Enter Mobile No !  </b>"));
        } else {
            String url = "http://192.168.191.218/E-hairsalon/check_mobile.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
                            if (response.trim().equals("EXIST")) {
//                                Toast.makeText(Signup.this, response, Toast.LENGTH_LONG).show();
                                mobile_check = true;
                                msg.setVisibility(View.VISIBLE);
                                msg.setText(Html.fromHtml("<b> Mobile No Already Exist, Use Another Mobile No !  </b>"));
                            } else if (response.trim().equals("NOT_EXIST")){
                                mobile_check = false;
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Signup.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("mobileno", mobileno);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
            requestQueue.add(stringRequest);
        }
    }

    public void insertData(View view) {

        String Base_Url = "http://192.168.191.218/E-hairsalon/user_registration.php";

        String Name = txtName.getText().toString();
        String Username = txtUsername.getText().toString();
        String Password = txtPassword.getText().toString();
        String mobileno = txtmobileno.getText().toString();
        String emailid = txtEmail_id.getText().toString();


        if (Name.equals("")) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Full Name Is Empty !  </b>"));
        } else if (mobileno.equals("")) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Mobile No Is Empty !  </b>"));
        } else if (emailid.equals("")) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Email id Is Empty !  </b>"));
        } else if (Username.equals("")) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Username Is Empty !  </b>"));
        } else if (Password.equals("")) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(Html.fromHtml("<b> Password Is Empty !  </b>"));
        } else {

            msg.setVisibility(View.GONE);
            boolean a = isValidMobile1(mobileno);
            if (a == true) {
                if (mobile_check == false) {
                    if (username_check == false) {
                        msg.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url,
                                response -> {
                                    if (response.trim().equalsIgnoreCase("Data insert successfully")) {

                                        Toast.makeText(Signup.this, "User Registration Successfully!", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                },
                                error -> Toast.makeText(Signup.this, error.getMessage(), Toast.LENGTH_LONG).show()) {
                            @NonNull
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("full_name", Name);
                                params.put("email_id", emailid);
                                params.put("mobile_no", mobileno);
                                params.put("username", Username);
                                params.put("password", Password);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
                        requestQueue.add(stringRequest);
                    } else {
                        msg.setVisibility(View.VISIBLE);
                        msg.setText(Html.fromHtml("<b> Username Already Exist, Use Another Username !  </b>"));
                    }
                } else {
                    msg.setVisibility(View.VISIBLE);
                    msg.setText(Html.fromHtml("<b> Mobile No Already Exist, Use Another Mobile No !  </b>"));
                }
            }


        }
    }

    public void Onclick_Login(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.putExtra("SITE_URL", SITE_URL); //localhost
        startActivity(intent);
        finish();
    }
}