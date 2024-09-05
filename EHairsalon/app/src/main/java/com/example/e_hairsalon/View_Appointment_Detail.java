package com.example.e_hairsalon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class View_Appointment_Detail extends AppCompatActivity {

    String fine_rate1, violation_type_id1, violation_type1;
    TextView msg, price_total_text, all_fine_total_text, pending_fine,appointment_date,appointment_date_txt,appointment_time_txt;

    RecyclerView rv_userList, rv_userList_pending;
    int fine_total, all_total_fine;

    View divider5;
    Button btn_book_appoint;
    Spinner ed_appointment_time;
    String username, User_id, SITE_URL;
    Button btn_challan;
    String Appointment_id, appointment_time_id, txt_appointment_date, total_amount;
    String service_type_id;
    SessionManager sessionManager;

    String[] arr_appointment_time = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_detail);

        sessionManager = new SessionManager(getApplicationContext());
        username = sessionManager.getUsername();
        User_id = sessionManager.getUser_ID();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Appointment Detail");

        Intent intent = getIntent();
        Appointment_id = intent.getStringExtra("Appointment_id");
//        service_type_id = Appointment_id;
//        Toast.makeText(Book_Appointment.this, "check_id value "+check_id, Toast.LENGTH_LONG).show();


        final Calendar calendar = Calendar.getInstance();

        price_total_text = findViewById(R.id.price_total);
        rv_userList = findViewById(R.id.rv_userList);

        appointment_date_txt = findViewById(R.id.certificate_type);
        appointment_time_txt = findViewById(R.id.firstname);

        search_salon_service();
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

    public void search_salon_service() {
        String url = "http://192.168.191.218/E-hairsalon/view_user_appointment_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(Book_Appointment.this, "response " + response, Toast.LENGTH_LONG).show();
                        Log.e("home", "response: " + response);
                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() > 0) {
//
                                JSONObject jb = (JSONObject) jsonArray.get(0);

//                                listView.setAdapter(null);
                                showJSON(response);
                            } else {
//                                msg.setText(Html.fromHtml("<b> No Record Found !  </b>"));
                            }


                        } catch (JSONException e) {
                            Toast.makeText(View_Appointment_Detail.this, "JSONException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

//                            listView.setAdapter(null);
                        }

//                            Toast.makeText(Search_Vehicle_Activity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(View_Appointment_Detail.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", User_id);
                params.put("Appointment_id", Appointment_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(View_Appointment_Detail.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {

//            Appointment_id = "";
            JSONArray jsonArray = new JSONArray(response);

//            listView.setAdapter(null);
//
            final String[] salon_service_id_arr = new String[jsonArray.length()];
            final String[] salon_service_type_arr = new String[jsonArray.length()];
            final String[] salon_service_time_arr = new String[jsonArray.length()];
            final String[] salon_service_price_arr = new String[jsonArray.length()];
            final String[] option_checked = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);

                String salon_service_record_id = jb.getString("id");
                String user_id = jb.getString("user_id");
                String appointment_book_date = jb.getString("appointment_book_date");
                String appointment_time = jb.getString("appointment_time");
                String total_amount = jb.getString("total_amount");
                String owner_txt = jb.getString("owner_txt");
                String appointment_status = jb.getString("appointment_status");


                String service_name = jb.getString("service_name");
                String service_time = jb.getString("service_time");
                String service_price = jb.getString("service_price");
                String service_final_price = jb.getString("service_final_price");
                String service_discount = jb.getString("service_discount");

                salon_service_id_arr[i] = jb.getString("id");
                salon_service_type_arr[i] = jb.getString("service_name");
                salon_service_time_arr[i] = jb.getString("service_time");

                fine_total = fine_total + jb.getInt("service_final_price");

                appointment_date_txt.setText(appointment_book_date);
                appointment_time_txt.setText(appointment_time);

                price_total_text.setText(Html.fromHtml("<b>Total  ₹. " + fine_total + "</b>"));

//                int price= Integer.parseInt(service_price);
//                int discount= Integer.parseInt(service_discount);
//                int final_price= price-discount;
//                salon_service_price_arr[i] = jb.getString(String.valueOf(final_price));
                salon_service_price_arr[i] = service_final_price;
                option_checked[i] = "No";
            }

            total_amount = String.valueOf(fine_total);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(View_Appointment_Detail.this, 1);
            rv_userList.setLayoutManager(mLayoutManager);

            rv_userList.addItemDecoration(new Add_Appointment.GridSpacingItemDecoration(1, 0, false));
            rv_userList.setItemAnimator(new DefaultItemAnimator());

            Ad_Appointment_Service_List ad_appointment_service_list = new Ad_Appointment_Service_List(View_Appointment_Detail.this, salon_service_id_arr, salon_service_type_arr,
                    salon_service_time_arr, salon_service_price_arr, option_checked, new Ad_Salon_Service_List.OnItemClickListener() {
                @Override
                public void onItemClick(Integer data, Boolean checkORNot) {

                }
            });

            rv_userList.setAdapter(ad_appointment_service_list);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}