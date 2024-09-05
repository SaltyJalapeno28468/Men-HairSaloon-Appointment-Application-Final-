package com.example.e_hairsalon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Package_Activity extends AppCompatActivity {

    String username;
    String User_id;
    String farm_id, crop_id;
    String tag = null;
    SessionManager sessionManager;
    ListView farmList;

    private MenuItem nav_notification;

    private RecyclerView FarmRV;
    private ArrayList<SalonServiceLVModel> salonserviceLVModelArrayList;
    private SalonServicePackageLVAdapter salonServicePackageLVAdapter;
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
        setContentView(R.layout.activity_add_package);

        sessionManager = new SessionManager(getApplicationContext());
        username = sessionManager.getUsername();
        User_id = sessionManager.getUser_ID();

        FarmRV = findViewById(R.id.farmList);
        salonserviceLVModelArrayList = new ArrayList<>();
        salonServicePackageLVAdapter = new SalonServicePackageLVAdapter(this, salonserviceLVModelArrayList, itemClickListener);
        FarmRV.setAdapter(salonServicePackageLVAdapter);
        search_salon_service_status();
    }

    private void search_salon_service_status() {
        String url = "http://192.168.191.218/E-hairsalon/view_all_salon_service_package.php";
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
                            Toast.makeText(Add_Package_Activity.this, "No Record Found ", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(Add_Package_Activity.this, "JSONException ", Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Package_Activity.this);
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
            salonServicePackageLVAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Toast.makeText(Add_Package_Activity.this, "catch " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}