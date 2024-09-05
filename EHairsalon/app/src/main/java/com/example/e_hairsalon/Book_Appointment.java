package com.example.e_hairsalon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Book_Appointment extends AppCompatActivity {

    String fine_rate1, violation_type_id1, violation_type1;
    TextView msg, price_total_text, all_fine_total_text, pending_fine;

    RecyclerView rv_userList, rv_userList_pending;
    int fine_total, all_total_fine;

    View divider5;
    Button btn_book_appoint;

    EditText appointment_date;
    Spinner ed_appointment_time;
    String username, User_id, SITE_URL;
    Button btn_challan;
    String check_id, appointment_time_id, appointment_time, txt_appointment_date, total_amount;
    String service_type_id;
    SessionManager sessionManager;

    String[] arr_appointment_time = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        sessionManager = new SessionManager(getApplicationContext());
        username = sessionManager.getUsername();
        User_id = sessionManager.getUser_ID();

        Intent intent = getIntent();
        check_id = intent.getStringExtra("check_id");
        service_type_id = check_id;
//        Toast.makeText(Book_Appointment.this, "check_id value "+check_id, Toast.LENGTH_LONG).show();


        final Calendar calendar = Calendar.getInstance();
        appointment_date = findViewById(R.id.birth_date);
        ed_appointment_time = findViewById(R.id.certificate_type);
        btn_book_appoint = findViewById(R.id.btn_book_appoint);
        msg = findViewById(R.id.msg);

        price_total_text = findViewById(R.id.price_total);
        rv_userList = findViewById(R.id.rv_userList);

        search_salon_service();


        ed_appointment_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appointment_time_id = String.valueOf(ed_appointment_time.getSelectedItemId());
                appointment_time = String.valueOf(ed_appointment_time.getSelectedItem());

//                Toast.makeText(getApplicationContext(), "appointment_time " + appointment_time, Toast.LENGTH_LONG).show();

                if (appointment_time_id.equals("1")) {
                } else if (appointment_time_id.equals("2")) {
                } else if (appointment_time_id.equals("0")) {
                    appointment_time = "";
                    Toast.makeText(getApplicationContext(), "Select Appointment Time ", Toast.LENGTH_LONG).show();


                }
//                Toast.makeText(getApplicationContext(), "certificate_type_id " + certificate_type_id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Book_Appointment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                appointment_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                txt_appointment_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                check_appointment_time();
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.


//                calendar.add(Calendar.MONTH,5);
//                long afterTwoMonthsinMilli=calendar.getTimeInMillis();
//                datePickerDialog.getDatePicker().setMaxDate(afterTwoMonthsinMilli);
//                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        btn_book_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Book_Appointment.this, "service_type_id "+service_type_id, Toast.LENGTH_LONG).show();
                if (service_type_id.equals("")) {
                    msg.setText("* Select Salon Service !");
                    msg.setVisibility(View.VISIBLE);
                } else if (txt_appointment_date.equals("")) {
                    msg.setText("* Select Appointment Date !");
                    msg.setVisibility(View.VISIBLE);
                } else if (appointment_time.equals("")) {
                    msg.setVisibility(View.VISIBLE);
                    msg.setText("* Select Appointment Time !");
                } else {
                    msg.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.191.218/E-hairsalon/book_appointment.php",
                            response -> {
                                if (response.trim().equals("Appointment Book Successfully")) {
//                                    if (response.equalsIgnoreCase("Data insert successfully")) {

                                    Toast.makeText(Book_Appointment.this, "Appointment Book Successfully!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("TAG", "response: " + response + " ");
                                    Toast.makeText(Book_Appointment.this, "Else PArt " + response, Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> Toast.makeText(Book_Appointment.this, error.getMessage(), Toast.LENGTH_LONG).show()) {
                        @NonNull
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(User_id));
                            params.put("check_id", String.valueOf(service_type_id));
                            params.put("appointment_date", String.valueOf(txt_appointment_date));
                            params.put("appointment_time", String.valueOf(appointment_time));
                            params.put("appointment_time_id", String.valueOf(appointment_time_id));
                            params.put("total_amount", String.valueOf(total_amount));
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(Book_Appointment.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void check_appointment_time() {

        String url = "http://192.168.191.218/E-hairsalon/check_date_appointment_time.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(Book_Appointment.this, "response " + response, Toast.LENGTH_LONG).show();
                        Log.e("home", "response: " + response);
                        try {

                            if (response.trim().equals("NO_Record_Found")) {

//                                Date currentTime = Calendar.getInstance().getTime();
//                                System.out.println("currentTime :" + currentTime);
                                Calendar c = Calendar.getInstance();
                                System.out.println("Current dateTime => " + c.getTime());
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                                String formattedDate = df.format(c.getTime());
                                System.out.println("Format dateTime => " + formattedDate);

                                SimpleDateFormat current_date = new SimpleDateFormat("yyyy-M-d");
                                String current_date1 = current_date.format(c.getTime());
                                System.out.println("Format date => " + current_date1);

                                SimpleDateFormat current_time = new SimpleDateFormat("H");
                                int current_time1 = Integer.parseInt(current_time.format(c.getTime()));
                                System.out.println("Format Time => " + current_time1);

                                SimpleDateFormat current_time_minit = new SimpleDateFormat("m");
                                int current_time_minit1 = Integer.parseInt(current_time_minit.format(c.getTime()));
                                System.out.println("Format dateTime_minit => " + current_time_minit1);

                                String[] appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};

                                if (current_date1.equals(txt_appointment_date)) {
                                    System.out.println("if current date => " + current_date1);
                                    System.out.println("if txt_appointment_date => " + txt_appointment_date);

                                    if (current_time1 < 8) {
                                        appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                    }

                                    if (current_time1 == 8) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 9) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 10) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 11) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 12) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 13) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 14) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 15) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 16) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 17) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 18) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            appointment_time_type = new String[]{"⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        }
                                    } else if (current_time1 == 19) {
                                        if (current_time_minit1 < 30) {
                                            appointment_time_type = new String[]{"⏱ 8:00 PM", "⏱ 8:30 PM"};
                                        } else {
                                            Toast.makeText(Book_Appointment.this, "Please Take Appointment ", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                } else {
                                    appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                                }


//                                String[] appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
//                                String[] arr_appointment_time = new String[appointment_time_type.length - 1];

                                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, appointment_time_type);
                                // on below line we are setting drop down view resource for our adapter.
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // on below line we are setting adapter for spinner.
                                ed_appointment_time.setAdapter(null);
                                ed_appointment_time.setAdapter(adapter);

                            } else {

                                JSONArray jsonArray = new JSONArray(response);

                                if (jsonArray.length() > 0) {
//
                                    JSONObject jb = (JSONObject) jsonArray.get(0);

//                                listView.setAdapter(null);
                                    showJSON_Appointment_time(response);
                                } else {
//                                msg.setText(Html.fromHtml("<b> No Record Found !  </b>"));
                                }
                            }


                        } catch (JSONException e) {
                            Toast.makeText(Book_Appointment.this, "JSONException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

//                            listView.setAdapter(null);
                        }

//                            Toast.makeText(Search_Vehicle_Activity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Book_Appointment.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_date", txt_appointment_date);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Book_Appointment.this);
        requestQueue.add(stringRequest);


    }

    private void showJSON_Appointment_time(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {

//            check_id = "";
            JSONArray jsonArray = new JSONArray(response);

//            listView.setAdapter(null);

            Calendar c = Calendar.getInstance();
            System.out.println("Current dateTime => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
            String formattedDate = df.format(c.getTime());
            System.out.println("Format dateTime => " + formattedDate);

            SimpleDateFormat current_date = new SimpleDateFormat("yyyy-M-d");
            String current_date1 = current_date.format(c.getTime());
            System.out.println("Format date => " + current_date1);

            SimpleDateFormat current_time = new SimpleDateFormat("H");
            int current_time1 = Integer.parseInt(current_time.format(c.getTime()));
            System.out.println("Format Time => " + current_time1);

            SimpleDateFormat current_time_minit = new SimpleDateFormat("m");
            int current_time_minit1 = Integer.parseInt(current_time_minit.format(c.getTime()));
            System.out.println("Format dateTime_minit => " + current_time_minit1);

            String[] appointment_time_type = new String[]{};
            if (current_date1.equals(txt_appointment_date)) {
                System.out.println("if current date => " + current_date1);
                System.out.println("if txt_appointment_date => " + txt_appointment_date);

                if (current_time1 < 8) {
                    appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                }

                if (current_time1 == 8) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 9) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 10) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 11) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 12) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 13) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 14) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 15) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 16) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 17) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 18) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        appointment_time_type = new String[]{"⏱ 8:00 PM", "⏱ 8:30 PM"};
                    }
                } else if (current_time1 == 19) {
                    if (current_time_minit1 < 30) {
                        appointment_time_type = new String[]{"⏱ 8:00 PM", "⏱ 8:30 PM"};
                    } else {
                        Toast.makeText(Book_Appointment.this, "Please Take Appointment ", Toast.LENGTH_LONG).show();
                    }
                }

            } else {
                appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};
            }

//            String[] appointment_time_type = new String[]{"⏱ 9:00 AM", "⏱ 9:30 AM", "⏱ 10:00 AM", "⏱ 10:30 AM", "⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM", "⏱ 2:00 PM", "⏱ 2:30 PM", "⏱ 3:00 PM", "⏱ 3:30 PM", "⏱ 4:00 PM", "⏱ 4:30 PM", "⏱ 5:00 PM", "⏱ 5:30 PM", "⏱ 6:00 PM", "⏱ 6:30 PM", "⏱ 7:00 PM", "⏱ 7:30 PM", "⏱ 8:00 PM", "⏱ 8:30 PM"};

//            String[] appointment_time_type = new String[]{"⏱ 11:00 AM", "⏱ 11:30 AM", "⏱ 12:00 PM", "⏱ 12:30 PM", "⏱ 1:00 PM", "⏱ 1:30 PM"};


//        int[] arr = new int[]{1,2,3,4,5};
//            String[] arr_appointment_time = new String[appointment_time_type.length - jsonArray.length()];
//            String[] arr_appointment_time = new String[appointment_time_type.length];
            String[] arr_appointment_time = new String[]{};

            if (current_date1.equals(txt_appointment_date)) {
//                System.out.println("if current date => " + current_date1);
//                System.out.println("if txt_appointment_date => " + txt_appointment_date);
                int Check_hour_time = 0;
                int cnt_Check_hour_time = 0;
                for (int ij = 0; ij < jsonArray.length(); ij++) {

                    JSONObject jbj = (JSONObject) jsonArray.get(ij);

                    String appointment_book_date = jbj.getString("appointment_book_date");
                    String appointment_time = jbj.getString("appointment_time");

                    if (appointment_time.equals("⏱ 9:00 AM")) {
                        Check_hour_time = 9;
                    } else if (appointment_time.equals("⏱ 9:30 AM")) {
                        Check_hour_time = 9;
                    } else if (appointment_time.equals("⏱ 10:00 AM")) {
                        Check_hour_time = 10;
                    } else if (appointment_time.equals("⏱ 10:30 AM")) {
                        Check_hour_time = 10;
                    } else if (appointment_time.equals("⏱ 11:00 AM")) {
                        Check_hour_time = 11;
                    } else if (appointment_time.equals("⏱ 11:30 AM")) {
                        Check_hour_time = 11;
                    } else if (appointment_time.equals("⏱ 12:00 PM")) {
                        Check_hour_time = 12;
                    } else if (appointment_time.equals("⏱ 12:30 PM")) {
                        Check_hour_time = 12;
                    } else if (appointment_time.equals("⏱ 1:00 PM")) {
                        Check_hour_time = 13;
                    } else if (appointment_time.equals("⏱ 1:30 PM")) {
                        Check_hour_time = 13;
                    } else if (appointment_time.equals("⏱ 2:00 PM")) {
                        Check_hour_time = 14;
                    } else if (appointment_time.equals("⏱ 2:30 PM")) {
                        Check_hour_time = 14;
                    } else if (appointment_time.equals("⏱ 3:00 PM")) {
                        Check_hour_time = 15;
                    } else if (appointment_time.equals("⏱ 3:30 PM")) {
                        Check_hour_time = 15;
                    } else if (appointment_time.equals("⏱ 4:00 PM")) {
                        Check_hour_time = 16;
                    } else if (appointment_time.equals("⏱ 4:30 PM")) {
                        Check_hour_time = 16;
                    } else if (appointment_time.equals("⏱ 5:00 PM")) {
                        Check_hour_time = 17;
                    } else if (appointment_time.equals("⏱ 5:30 PM")) {
                        Check_hour_time = 17;
                    } else if (appointment_time.equals("⏱ 6:00 PM")) {
                        Check_hour_time = 18;
                    } else if (appointment_time.equals("⏱ 6:30 PM")) {
                        Check_hour_time = 18;
                    } else if (appointment_time.equals("⏱ 7:00 PM")) {
                        Check_hour_time = 19;
                    } else if (appointment_time.equals("⏱ 7:30 PM")) {
                        Check_hour_time = 19;
                    } else if (appointment_time.equals("⏱ 8:00 PM")) {
                        Check_hour_time = 20;
                    } else if (appointment_time.equals("⏱ 8:30 PM")) {
                        Check_hour_time = 20;
                    }

                    if (current_time1 < Check_hour_time) {
                        cnt_Check_hour_time = cnt_Check_hour_time + 1;
                    }
                }
                arr_appointment_time = new String[appointment_time_type.length - cnt_Check_hour_time];
            } else {
                arr_appointment_time = new String[appointment_time_type.length - jsonArray.length()];
            }


            for (int j = 0, k = 0; j < appointment_time_type.length; j++) {

//                System.out.println("Before deletion :" + Arrays.toString(appointment_time_type));
//                System.out.println("After deletion :" + Arrays.toString(arr_appointment_time));

                int check_val = 0;
                int check_avail = 0;
                int json_arr_length = 1;
                for (int i = 0; i < jsonArray.length(); i++) {

                    if (check_avail == 0) {
                        JSONObject jb = (JSONObject) jsonArray.get(i);

                        String salon_service_record_id = jb.getString("id");
                        String appointment_book_date = jb.getString("appointment_book_date");
                        String appointment_time = jb.getString("appointment_time");
                        String appointment_status = jb.getString("appointment_status");


                        String j_str = appointment_time;
//                        System.out.println("jsonArray.length() :" + jsonArray.length());
//                        System.out.println("appointment_time :" + appointment_time);
//                        System.out.println("array value :" + appointment_time_type[j]);

                        if (appointment_time_type[j].trim().equals(j_str.trim())) {
//                            System.out.println(" if condition");
                            check_avail = 1;

                        } else {
                            if (appointment_time_type[j].trim() != j_str.trim()) {
//                                System.out.println("inside if condition");
                                if (check_val == 0) {

                                    if (json_arr_length == jsonArray.length()) {
                                        check_val = check_val + 1;
                                        check_avail = 0;
                                        arr_appointment_time[k] = appointment_time_type[j];
                                        k++;
                                    }
                                }
                            }
                        }
//                    if (appointment_time_type[j].trim() != j_str.trim()) {
//                        System.out.println("inside if condition");
//                        if(check_val==0) {
//                            check_val = check_val + 1;
//                            arr_appointment_time[k] = appointment_time_type[j];
//                            k++;
//                        }
//                    }
                        System.out.println(" ");
//                        System.out.println("Before deletion :" + Arrays.toString(appointment_time_type));
//                        System.out.println("After deletion :" + Arrays.toString(arr_appointment_time));
                    }
                    json_arr_length = json_arr_length + 1;
                }
                check_val = 0;
            }

            System.out.println("Before deletion :" + Arrays.toString(appointment_time_type));
            System.out.println("After deletion :" + Arrays.toString(arr_appointment_time));

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr_appointment_time);

            // on below line we are setting drop down view resource for our adapter.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // on below line we are setting adapter for spinner.
//            ed_appointment_time.setAdapter(null);
            ed_appointment_time.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void search_salon_service() {
        String url = "http://192.168.191.218/E-hairsalon/view_all_search_salon_service.php";
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
                            Toast.makeText(Book_Appointment.this, "JSONException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();

//                            listView.setAdapter(null);
                        }

//                            Toast.makeText(Search_Vehicle_Activity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Book_Appointment.this, "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", User_id);
                params.put("check_id", check_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Book_Appointment.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
//        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {

            check_id = "";
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

                fine_total = fine_total + jb.getInt("service_final_price");

                price_total_text.setText(Html.fromHtml("<b>Total  ₹. " + fine_total + "</b>"));

//                int price= Integer.parseInt(service_price);
//                int discount= Integer.parseInt(service_discount);
//                int final_price= price-discount;
//                salon_service_price_arr[i] = jb.getString(String.valueOf(final_price));
                salon_service_price_arr[i] = service_final_price;
                option_checked[i] = "No";
            }

            total_amount = String.valueOf(fine_total);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Book_Appointment.this, 1);
            rv_userList.setLayoutManager(mLayoutManager);

            rv_userList.addItemDecoration(new Add_Appointment.GridSpacingItemDecoration(1, 0, false));
            rv_userList.setItemAnimator(new DefaultItemAnimator());

            Ad_Count_Salon_Service_List ad_count_salon_service_list = new Ad_Count_Salon_Service_List(Book_Appointment.this, salon_service_id_arr, salon_service_type_arr,
                    salon_service_time_arr, salon_service_price_arr, option_checked, new Ad_Salon_Service_List.OnItemClickListener() {
                @Override
                public void onItemClick(Integer data, Boolean checkORNot) {

//                    check_id = "";
//                    if (checkORNot == true) {
//
//                        option_checked[data] = "Yes";
//
//                        for (int j = 0; j < jsonArray.length(); j++) {
//                            if (option_checked[j].equals("Yes")) {
//                                check_id = check_id + salon_service_id_arr[j] + ",";
//
//                            }
//                        }
//                    } else {
//                        option_checked[data] = "No";
//
//                        for (int j = 0; j < jsonArray.length(); j++) {
//                            if (option_checked[j].equals("Yes")) {
//                                check_id = check_id + salon_service_id_arr[j] + ",";
//
//                            }
//                        }
//                    }
//                    Toast.makeText(getApplicationContext(), "check_id " + String.valueOf(check_id), Toast.LENGTH_LONG).show();
                }
            });

            rv_userList.setAdapter(ad_count_salon_service_list);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void Btn_Book_Appointment(View view) {
//
//
//    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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