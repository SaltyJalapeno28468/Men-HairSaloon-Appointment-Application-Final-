package com.example.e_hairsalon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class Add_Package_Appointment extends AppCompatActivity {

    String salon_service_id, salon_service_type, salon_service_price;
    RecyclerView rv_userList;
    SessionManager sessionManager;
    String username, User_id;

    String check_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package_appointment);

        sessionManager = new SessionManager(getApplicationContext());
        username = sessionManager.getUsername();
        User_id = sessionManager.getUser_ID();



        rv_userList = findViewById(R.id.rv_userList);
        search_salon_service();
    }

    public void search_salon_service() {
        String url = "http://192.168.191.218/E-hairsalon/view_all_salon_service_package.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                            Toast.makeText(Add_Package_Appointment.this, "JSONException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

//                            listView.setAdapter(null);
                        }

//                            Toast.makeText(Search_Vehicle_Activity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_Package_Appointment.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", User_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Add_Package_Appointment.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {

//            check_id = "";
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
                String service_name = jb.getString("service_name");
                String service_time = jb.getString("service_time");
                String service_price = jb.getString("service_price");
                String service_final_price = jb.getString("service_final_price");
                String service_discount = jb.getString("service_discount");

                salon_service_id_arr[i] = jb.getString("id");
                salon_service_type_arr[i] = jb.getString("service_name");
                salon_service_time_arr[i] = jb.getString("service_time");

//                int price= Integer.parseInt(service_price);
//                int discount= Integer.parseInt(service_discount);
//                int final_price= price-discount;
//                salon_service_price_arr[i] = jb.getString(String.valueOf(final_price));
                salon_service_price_arr[i] = service_final_price;
                option_checked[i] = "No";
            }

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Add_Package_Appointment.this, 1);
            rv_userList.setLayoutManager(mLayoutManager);

            rv_userList.addItemDecoration(new Add_Appointment.GridSpacingItemDecoration(1, 0, false));
            rv_userList.setItemAnimator(new DefaultItemAnimator());

            Ad_Salon_Service_List ad_salon_service_list = new Ad_Salon_Service_List(Add_Package_Appointment.this, salon_service_id_arr, salon_service_type_arr,
                    salon_service_time_arr, salon_service_price_arr, option_checked, new Ad_Salon_Service_List.OnItemClickListener() {
                @Override
                public void onItemClick(Integer data, Boolean checkORNot) {

                    check_id = "";
                    if (checkORNot == true) {

                        option_checked[data] = "Yes";

                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (option_checked[j].equals("Yes")) {
                                check_id = check_id + salon_service_id_arr[j] + ",";

                            }
                        }
                    } else {
                        option_checked[data] = "No";

                        for (int j = 0; j < jsonArray.length(); j++) {
                            if (option_checked[j].equals("Yes")) {
                                check_id = check_id + salon_service_id_arr[j] + ",";

                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(), "check_id " + String.valueOf(check_id), Toast.LENGTH_LONG).show();
                }
            });

            rv_userList.setAdapter(ad_salon_service_list);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Btn_Book_Appointment(View view) {
        check_id = check_id.replaceAll(",$", "");

        if (check_id.equals("")) {
//            msg.setText(Html.fromHtml("<b> Select Violation Type !  </b>"));
            Toast.makeText(Add_Package_Appointment.this, "Select Any Salon Service " + check_id, Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(Add_Appointment.this, "check_id " + check_id, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Book_Appointment.class);
            intent.putExtra("check_id", check_id);

            intent.putExtra("username",username);
//                intent.putExtra("police_user_id",);

//        finish();
            startActivity(intent);
        }
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}