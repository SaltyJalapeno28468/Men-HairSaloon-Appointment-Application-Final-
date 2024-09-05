package com.example.e_hairsalon;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Ad_Salon_Service_List extends RecyclerView.Adapter<Ad_Salon_Service_List.HorizontalViewHolder> {


    private final Activity context;
    private String[] salon_service_id_arr;
    private String[] salon_service_type_arr;
    private String[] salon_service_time_arr;
    private String[] salon_service_price_arr;
    private String[] option_checked;

    View view;

    //create interface for change image
    public interface OnItemClickListener {
        void onItemClick(Integer data,Boolean checkORNot);
    }

    private final OnItemClickListener listener;

    public Ad_Salon_Service_List(Activity context, String[] salonServiceIdArr, String[] salonServiceTypeArr, String[] salonServiceTimeArr, String[] salonServicePriceArr, String[] optionChecked, OnItemClickListener listener) {
        this.context = context;
        salon_service_id_arr = salonServiceIdArr;
        salon_service_type_arr = salonServiceTypeArr;
        salon_service_time_arr = salonServiceTimeArr;
        salon_service_price_arr = salonServicePriceArr;
        option_checked = optionChecked;
        this.listener = listener;
    }

    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.salon_service_list_item,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

//            holder.service_name.setText(salon_service_time_arr[position]+" min ");
//            holder.service_price.setText("₹. "+salon_service_price_arr[position]);
            holder.salon_service_type_new.setText(salon_service_type_arr[position]+"          ₹." +salon_service_price_arr[position]+"/" +salon_service_time_arr[position]+" min ");
//            holder.salon_service_type_new.setText(Html.fromHtml(salon_service_type_arr[position]));

//            holder.violation_type_new.setText(fine_rate1[position]);
        }
        catch (Exception e)
        {

        }
        catch (OutOfMemoryError oom)
        {

        }

//        holder.salon_service_type_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            }
//        });

        holder.salon_service_type_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
//                    Toast.makeText(context, "select item : " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),true);
                }
                else
                {
//                    Toast.makeText(context, "unchecked: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),false);
                }
            }
        });

//        holder.salon_service_type_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Toast.makeText(context, "select item  : " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
////                    listener.onItemClick(holder.getAdapterPosition(),true);
////                    listener.onItemClick(holder.);
//                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),true);
//
//                }
//                else
//                {
//                    Toast.makeText(context, "unchecked: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
////                    listener.onItemClick(holder.getAdapterPosition(),false);
//                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),false);
//                }
//            }
//        });

    }



    @Override
    public int getItemCount() {
        return salon_service_type_arr.length;
    }

    public  class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView service_name,service_price;
        CheckBox salon_service_type_new;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            salon_service_type_new= (CheckBox) itemView.findViewById (R.id.salon_service_type_new);
//            service_name= (TextView) itemView.findViewById (R.id.service_name);
//            service_price= (TextView) itemView.findViewById (R.id.service_price);

        }


        public Integer getAbsoluteAdapterPosition() {
            return getAdapterPosition();
        }
    }

}
