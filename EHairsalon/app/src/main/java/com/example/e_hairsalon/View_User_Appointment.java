package com.example.e_hairsalon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_User_Appointment extends AppCompatActivity {

    String username;
    String user_id;
    String farm_id, crop_id;
    String tag = null;
    SessionManager sessionManager;

    String SITE_URL = "http://192.168.191.218/E-nagar/";  //Localhost

    ListView farmList;

    private MenuItem nav_notification;

    private RecyclerView FarmRV;
    private ArrayList<UserAppointmentLVModel> userAppointmentLVModelArrayList;
    private UserAppointmentLVAdapter userAppointmentLVAdapter;
    ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_appointment);

        sessionManager = new SessionManager(getApplicationContext());

//        String App_lang=sessionManager.getApp_language();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Appointment");


        Intent intent = getIntent();
        username = sessionManager.getUsername();
        user_id = sessionManager.getUser_ID();
//        Toast.makeText(View_User_Appointment.this, "user_id "+user_id, Toast.LENGTH_LONG).show();

        FarmRV = findViewById(R.id.farmList);
        userAppointmentLVModelArrayList = new ArrayList<>();
        userAppointmentLVAdapter = new UserAppointmentLVAdapter(this, userAppointmentLVModelArrayList, itemClickListener);
        FarmRV.setAdapter(userAppointmentLVAdapter);
        search_user_appointment_status();
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

    private void search_user_appointment_status() {
        String url = "http://192.168.191.218/E-hairsalon/view_user_appointment.php";
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
                            Toast.makeText(View_User_Appointment.this, "No Record Found ", Toast.LENGTH_LONG).show();
                        }else {

                            userAppointmentLVModelArrayList.clear();
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
                                Toast.makeText(View_User_Appointment.this, "JSONException ", Toast.LENGTH_LONG).show();
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
                params.put("user_id", user_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(View_User_Appointment.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray jsonArray = new JSONArray(response);

//            farmList.setAdapter(null);
//
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);

                final HashMap<String, String> farm_data = new HashMap<>();

                String appointment_record_id = jb.getString("id");
                String appointment_book_date = jb.getString("appointment_book_date");
                String appointment_time = jb.getString("appointment_time");
                String total_amount = jb.getString("total_amount");
                String owner_txt = jb.getString("owner_txt");
                String appointment_status = jb.getString("appointment_status");
                String created_date = jb.getString("appointment_date");
//                Toast.makeText(View_User_Appointment.this, "appointment_book_date "+appointment_book_date, Toast.LENGTH_LONG).show();

                list.add(farm_data);

                userAppointmentLVModelArrayList.add(new UserAppointmentLVModel(appointment_record_id,user_id,appointment_book_date,appointment_time,total_amount,owner_txt ,appointment_status,created_date));

            }

            userAppointmentLVAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Toast.makeText(View_User_Appointment.this, "catch " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}