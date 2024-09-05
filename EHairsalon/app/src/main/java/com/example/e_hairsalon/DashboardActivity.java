package com.example.e_hairsalon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    String username;
    String User_id;
    String farm_id, crop_id;
    String tag = null;
    SessionManager sessionManager;
    ListView farmList;

    private MenuItem nav_notification;

    private RecyclerView FarmRV;
    private ArrayList<SalonServiceLVModel> salonserviceLVModelArrayList;
    private SalonServiceLVAdapter salonserviceLVAdapter;
    ItemClickListener itemClickListener;

    BottomNavigationView bottomNavigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String SITE_URL = "http://192.168.191.218/E-nagar/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);



//        SharedPref1.setLocale(this, SharedPref1.getLocale(this));
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_dashboard);

//        toolbar = findViewById(R.id.toolbar);




        bottomNavigationView = findViewById(R.id.bottomNavigation);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        DrawerArrowDrawable drawerArrowDrawable = new DrawerArrowDrawable(DashboardActivity.this);
        drawerArrowDrawable.setColor(Color.WHITE);
        toggle.setDrawerArrowDrawable(drawerArrowDrawable);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //load jump frag


        sessionManager = new SessionManager(getApplicationContext());
        username = sessionManager.getUsername();
        User_id = sessionManager.getUser_ID();
//        Toast.makeText(DashboardActivity.this, "User_id "+User_id, Toast.LENGTH_SHORT).show();

        if (sessionManager.getLogin()) {
//            Toast.makeText(DashboardActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//
////            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//            intent.putExtra("SITE_URL", SITE_URL); //localhost
//            startActivity(intent);
//            finish();
        }else{
//            Toast.makeText(DashboardActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Login.class);

//            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("SITE_URL", SITE_URL); //localhost
            startActivity(intent);
            finish();
        }

//        if (SharedPref1.getUserType(this).equals(SharedPref1.INSTITUTE)){
//            getIUser(SharedPref1.getUserId(this));
//        } else if (SharedPref1.getUserType(this).equals(SharedPref1.PERSONAL)) {
//            getPUser(SharedPref1.getUserId(this));
//        }
        if (getIntent().getStringExtra("frag")!=null){
            if (getIntent().getStringExtra("frag").equals("profile")){
//                loadFrag(new ProfileFragment(),true);
            }else if (getIntent().getStringExtra("frag").equals("data")){
//                loadFrag(new DataFragment(), true);
            }
        } else {
//            loadFrag(new JumpFragment(), true);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId()==R.id.home_drawer){
//                Log.e("home", "onCreate: "+ sessionManager.getDevicesDetail(getApplicationContext()));
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
                finish();
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if(item.getItemId()==R.id.book_package){
//                change_language_app();
                Intent intent = new Intent(DashboardActivity.this, Add_Package_Activity.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if(item.getItemId()==R.id.language_drawer){
//                change_language_app();
                Intent intent = new Intent(DashboardActivity.this, View_User_Appointment.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if(item.getItemId()==R.id.logout_drawer){
                Onclick_logout();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.jump){
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
                finish();
            } else if (item.getItemId()==R.id.data) {
                //        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, View_User_Appointment.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
//                finish();
            } else if (item.getItemId()==R.id.profile) {
//                loadFrag(new ProfileFragment(), false);
//                Toast.makeText(DashboardActivity.this, "profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("username", username);
//                        intent.putExtra("user_id", );
                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
//                finish();
            }
            return true;
        });


        FarmRV = findViewById(R.id.farmList);
        salonserviceLVModelArrayList = new ArrayList<>();
        salonserviceLVAdapter = new SalonServiceLVAdapter(this, salonserviceLVModelArrayList, itemClickListener);
        FarmRV.setAdapter(salonserviceLVAdapter);
        search_salon_service_status();
    }

    public void change_language_app() {

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

//        Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
//        intent.putExtra("username", username);
////                        intent.putExtra("user_id", );
//        intent.putExtra("SITE_URL", SITE_URL); //localhost
//        startActivity(intent);
//        finish();
    }

    public void Onclick_logout() {
//        SessionManager sessionManager=new SessionManager(context);
        String App_lang=sessionManager.getApp_language();
        String Message1="Are you sure log out?";
        if (App_lang.equals("")) {
            Message1="Are you sure log out?";
        } else if (App_lang.equals("hi")) {
            Message1="क्या आप ऐप से लॉग आउट करना चाहते हैं?";
        } else if (App_lang.equals("gu")) {
            Message1="શું તમે ખરેખર એપ્લિકેશનમાંથી લૉગ આઉટ કરવા માંગો છો?";
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Log out");
        builder.setMessage(Message1);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sessionManager.setLogin(false);
                sessionManager.setUsername("");
                sessionManager.setUser_ID("");

                Intent intent = new Intent(getApplicationContext(), Login.class);
                finish();

                intent.putExtra("SITE_URL", SITE_URL); //localhost
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
//        AlertDialog alertDialog = builder.create();
//        AlertDialog alertDialog=builder.create();
//        alertDialog.show();
        AlertDialog alertDialog= builder.show();
    }

//    public void add_complaint(View view) {
////        Toast.makeText(DashboardActivity.this, "add complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, Add_ComplaintActivity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }
//
//    public void search_complaint(View view) {
////        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, View_Complaint_Status_Activity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }
//
//    public void add_certificate_request(View view) {
////        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, Add_Certificate_requestActivity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }
//
//    public void view_certificate_request(View view) {
////        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, View_Certificate_Status_Activity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }
//
//    public void apply_shop_certificate(View view) {
////        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, Apply_Shop_Certificate_Activity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }
//
//    public void view_shop_certificate(View view) {
////        Toast.makeText(DashboardActivity.this, "Search complaint", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, View_Shop_Certificate_Status_Activity.class);
//        intent.putExtra("username", username);
//        intent.putExtra("user_id", User_id);
////        finish();
//        startActivity(intent);
//    }

    private void search_salon_service_status() {
        String url = "http://192.168.191.218/E-hairsalon/view_all_salon_service.php";
//        Toast.makeText(View_Generated_Challan_User_Activity.this, "url "+url, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//                            TextView msg = findViewById(R.id.msg);
//                            msg.setText(Html.fromHtml("<b> Record Not Found !  </b>"+response));
                        Log.e("TAG", "response: "+response+" ");
//                        Toast.makeText(View_Complaint_Status_Activity.this, response, Toast.LENGTH_LONG).show();

                        if(response.trim().equals("NO_Record_Found")){
                            Toast.makeText(DashboardActivity.this, "No Record Found ", Toast.LENGTH_LONG).show();
                        }else {

                            salonserviceLVModelArrayList.clear();
                            try {

                                JSONArray jsonArray = new JSONArray(response);

                                if (jsonArray.length() > 0) {
//
                                    JSONObject jb = (JSONObject) jsonArray.get(0);

//                                farmList.setAdapter(null);
                                    showJSON(response);
                                } else {
//                                farmList.setAdapter(null);

//                                msg.setText(Html.fromHtml("<b> No Record Found !  </b>"));
//                                Toast.makeText(Dashboard.this, "Record Not Found ", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(DashboardActivity.this, "JSONException ", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
//                            progressBar.setVisibility(View.GONE);
//                                farmList.setAdapter(null);
                            }
                        }
//                            Toast.makeText(Search_Vehicle_Activity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(View_Generated_Challan_User_Activity.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                    params.put("vehicle_no", vehicle_no_text);
                params.put("user_id", User_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {

//            Toast.makeText(Dashboard.this, "showJSON ", Toast.LENGTH_LONG).show();
            JSONArray jsonArray = new JSONArray(response);

//            farmList.setAdapter(null);
//
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);

//                farm_id = jb.getString("tbl_farm_id");


                final HashMap<String, String> farm_data = new HashMap<>();

                String salon_service_record_id = jb.getString("id");
                String service_name = jb.getString("service_name");
                String service_time = jb.getString("service_time");
                String service_price = jb.getString("service_price");
                String service_discount = jb.getString("service_discount");
                String img_url = jb.getString("img_url");
                String created_date = jb.getString("created_date");
//                Toast.makeText(Dashboard.this, "farm_name "+farm_name, Toast.LENGTH_LONG).show();

                list.add(farm_data);

                salonserviceLVModelArrayList.add(new SalonServiceLVModel(salon_service_record_id,User_id,service_name,service_time,service_price,service_discount ,img_url,created_date));

            }
            salonserviceLVAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Toast.makeText(DashboardActivity.this, "catch " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}