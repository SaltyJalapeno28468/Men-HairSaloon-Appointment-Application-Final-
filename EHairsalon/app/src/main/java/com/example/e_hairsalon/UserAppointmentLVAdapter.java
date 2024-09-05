package com.example.e_hairsalon;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAppointmentLVAdapter extends RecyclerView.Adapter<UserAppointmentLVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserAppointmentLVModel> userAppointmentLVModelArrayList;
    private ItemClickListener itemClickListener;
    int selectedPosition = 1;
    View_User_Appointment view_user_appointment;

    public UserAppointmentLVAdapter(Context context, ArrayList<UserAppointmentLVModel> userAppointmentLVModelArrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.userAppointmentLVModelArrayList = userAppointmentLVModelArrayList;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public UserAppointmentLVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_user_appointment_status, parent, false);
        return new UserAppointmentLVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAppointmentLVAdapter.ViewHolder holder, int position) {
        UserAppointmentLVModel farmLVModel = userAppointmentLVModelArrayList.get(position);
        holder.certificate_date.setText(farmLVModel.getAppointment_book_date());
        holder.certificate_type.setText(" - " + farmLVModel.getAppointment_book_date());
        holder.firstname.setText(" " + farmLVModel.getAppointment_time());

        if (farmLVModel.getOwner_txt().equals("null")) {
            holder.owner_txt.setText(" ");
        } else {
            holder.llowner_txt.setVisibility(View.VISIBLE);
            holder.owner_txt.setText(" " + farmLVModel.getOwner_txt());
        }
        if (farmLVModel.getAppointment_status().equals("null")) {
            holder.status.setText(" ");
        } else {
            holder.llstatus.setVisibility(View.VISIBLE);
            holder.status.setText(" " + farmLVModel.getAppointment_status());
        }

        holder.btn_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), View_Appointment_Detail.class);
                intent.putExtra("URL", "");
                intent.putExtra("user_id", farmLVModel.getUser_id());
                intent.putExtra("Appointment_id", farmLVModel.getAppointment_record_id());
                intent.putExtra("SITE_URL", ""); //localhost
                context.startActivity(intent);

                notifyDataSetChanged();
            }
        });

        holder.farm_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                UserAppointmentLVModel freezerLVModel4 = userAppointmentLVModelArrayList.get(position);
//                Toast.makeText(context, "position " + position, Toast.LENGTH_LONG).show();

                PopupMenu popupMenu = new PopupMenu(context, holder.farm_options, Gravity.START);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//                        if(item.getItemId()==R.id.menu1_edit){
//                            Intent intent = new Intent(context.getApplicationContext(), Edit_CertificateActivity.class);
//                            intent.putExtra("URL", "");
//                            intent.putExtra("user_id", farmLVModel.getUser_id());
//                            intent.putExtra("certificate_id", farmLVModel.getAppointment_record_id());
//                            intent.putExtra("SITE_URL", ""); //localhost
//                            context.startActivity(intent);
//
//                            notifyDataSetChanged();
////
//                            return true;
//                        }else
                        if (item.getItemId() == R.id.menu2_delete) {
//                            Toast.makeText(context, "Delete Click " + farmLVModel4.getFarm_id() + farmLVModel4.getFarm_name(), Toast.LENGTH_LONG).show();
                            Delete_recycleview(farmLVModel.getAppointment_record_id(), farmLVModel.getUser_id());
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userAppointmentLVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView idSensorData;
        LinearLayout llowner_txt, llstatus;
        TextView certificate_type, firstname, certificate_date, owner_txt, status;
        Menu menu1_edit, menu2_delete;
        ImageButton farm_options;
        Button btn_appoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            certificate_date = itemView.findViewById(R.id.created_date);
            certificate_type = itemView.findViewById(R.id.certificate_type);
            firstname = itemView.findViewById(R.id.firstname);
            owner_txt = itemView.findViewById(R.id.owner_txt);
//
            status = itemView.findViewById(R.id.status);
            llowner_txt = itemView.findViewById(R.id.llowner_txt);
            llstatus = itemView.findViewById(R.id.llstatus);

            btn_appoint = itemView.findViewById(R.id.btn_appoint);
////
////
//            menu1_edit = itemView.findViewById(R.id.menu1_edit);
            menu2_delete = itemView.findViewById(R.id.menu2_delete);
//
            farm_options = itemView.findViewById(R.id.farm_options);


        }
    }

    public void Delete_recycleview(String certificate_id, String user_id) {

        SessionManager sessionManager = new SessionManager(context);
        String App_lang = sessionManager.getApp_language();
        String Message1 = "Are you sure Cancel Your Appointment ?";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage(Message1);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(context, "Freezer_record_id "+Freezer_record_id, Toast.LENGTH_LONG).show();
                insertData(certificate_id);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
        AlertDialog alertDialog = builder.show();
    }

    public void insertData(String certificate_id) {
        SessionManager sessionManager = new SessionManager(context);
        String SITE_URL = sessionManager.getSite_Url();
        String Base_Url = "http://192.168.191.218/E-hairsalon/appointment_delete.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Base_Url,
                response -> {
                    if (response.trim().equals("Record_Delete")) {
//                        Toast.makeText(Dashboard.this, "No Record Found ", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "Appointment Cancel Successfully!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, DashboardActivity.class);
                        context.startActivity(intent);
//                        dashboard.finish();
                    } else {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                    }
                },
                error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show()) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("appointment_id", certificate_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
