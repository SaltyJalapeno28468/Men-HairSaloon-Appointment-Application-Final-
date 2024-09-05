package com.example.e_hairsalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String username,mobile_no,name,email;

    TextView txt_name,txt_mobile_no,txt_email,txt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email=sessionManager.getemail();
        mobile_no=sessionManager.getmobile_no();
        name=sessionManager.getname();
//        user_id = intent.getStringExtra("user_id");

        txt_username = findViewById(R.id.usernameTv);
        txt_name = findViewById(R.id.name);
        txt_mobile_no = findViewById(R.id.mobile_number);
        txt_email=findViewById(R.id.email);

        txt_username.setText(username);
        txt_name.setText("Name - "+name);
        txt_mobile_no.setText("Mobile No - "+mobile_no);
        txt_email.setText("Email - "+email);

        String App_lang=sessionManager.getApp_language();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (App_lang.equals("")) {
            getSupportActionBar().setTitle("Profile");
        } else if (App_lang.equals("hi")) {
            getSupportActionBar().setTitle("प्रोफाइल");
        } else if (App_lang.equals("gu")) {
            getSupportActionBar().setTitle("પ્રોફાઇલ");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent1 = new Intent(this, Dashboard.class);
////        Intent intent1 = new Intent(this, HomeActivity.class);
//        finish();
//        startActivity(intent1);
    }

//    public boolean onSupportNavigateUp() {
////        onBackPressed();
//        Intent intent1 = new Intent(this, DashboardActivity.class);
////        Intent intent1 = new Intent(this, HomeActivity.class);
//        finish();
//        startActivity(intent1);
//        return true;
//    }

}