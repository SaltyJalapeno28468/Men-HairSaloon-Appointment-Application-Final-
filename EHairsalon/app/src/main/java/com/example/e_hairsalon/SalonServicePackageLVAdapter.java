package com.example.e_hairsalon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SalonServicePackageLVAdapter extends RecyclerView.Adapter<SalonServicePackageLVAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<SalonServiceLVModel> salonServiceLVModelArrayList;
    private ItemClickListener itemClickListener;
    int selectedPosition = 1;
    DashboardActivity dashboardActivity;

    public SalonServicePackageLVAdapter(Context context, ArrayList<SalonServiceLVModel> salonServiceLVModelArrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.salonServiceLVModelArrayList = salonServiceLVModelArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SalonServicePackageLVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_salon_service, parent, false);
        return new SalonServicePackageLVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonServicePackageLVAdapter.ViewHolder holder, int position) {
        SalonServiceLVModel farmLVModel = salonServiceLVModelArrayList.get(position);
        holder.service_name.setText(farmLVModel.getService_name());
        holder.service_time.setText(farmLVModel.getService_time()+" min");
        int price= Integer.parseInt(farmLVModel.getService_price());
        int discount= Integer.parseInt(farmLVModel.getService_discount());
        int final_price=price-discount;
        holder.service_price.setText( "₹ "+final_price);

        String url_localhost="http://192.168.191.218/E-hairsalon/";
        // on below line we are checking if the image file exist or not.

        Glide.with(context).load(url_localhost+farmLVModel.getImg_url()).into(holder.img1);

        holder.btn_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                SalonServiceLVModel freezerLVModel4 = salonServiceLVModelArrayList.get(position);

                Toast.makeText(context, "position " + farmLVModel.getSalon_service_record_id()+" user id "+ farmLVModel.getUser_id(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context.getApplicationContext(), Add_Package_Appointment.class);
                intent.putExtra("URL", "");
                intent.putExtra("user_id", farmLVModel.getUser_id());
                intent.putExtra("Salon_service_record_id", farmLVModel.getSalon_service_record_id());
                intent.putExtra("SITE_URL", ""); //localhost
                context.startActivity(intent);
//                Toast.makeText(context, "Id " + farmLVModel.getSalon_service_record_id()+" user id "+ farmLVModel.getUser_id(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return salonServiceLVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView idSensorData;
        TextView service_name,service_time,service_price;
        Menu menu1_edit, menu2_delete;
        ImageButton farm_options;
        ImageView img1;
        Button btn_appoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name= itemView.findViewById(R.id.service_name);
            service_time = itemView.findViewById(R.id.service_time);
            service_price = itemView.findViewById(R.id.service_price);
            img1 = itemView.findViewById(R.id.img1);

            btn_appoint = itemView.findViewById(R.id.btn_appoint);
//            certificate_link = itemView.findViewById(R.id.certificate_link);
//            certificate_link.setMovementMethod(LinkMovementMethod.getInstance());
//
////
//            idSensorData = itemView.findViewById(R.id.idSensorData);
////
////
//            menu1_edit = itemView.findViewById(R.id.menu1_edit);
//            menu2_delete = itemView.findViewById(R.id.menu2_delete);
////
//            farm_options = itemView.findViewById(R.id.farm_options);


        }
    }
}
