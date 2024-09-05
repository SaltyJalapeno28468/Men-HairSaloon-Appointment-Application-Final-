package com.example.e_hairsalon;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class Ad_Appointment_Service_List extends RecyclerView.Adapter<Ad_Appointment_Service_List.HorizontalViewHolder> {

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

    public Ad_Appointment_Service_List(Activity context, String[] salonServiceIdArr, String[] salonServiceTypeArr, String[] salonServiceTimeArr, String[] salonServicePriceArr, String[] optionChecked, Ad_Salon_Service_List.OnItemClickListener listener) {
        this.context = context;
        salon_service_id_arr = salonServiceIdArr;
        salon_service_type_arr = salonServiceTypeArr;
        salon_service_time_arr = salonServiceTimeArr;
        salon_service_price_arr = salonServicePriceArr;
        option_checked = optionChecked;
    }

    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.appointment_service_list_count_item,parent,false);
        return new Ad_Appointment_Service_List.HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Ad_Appointment_Service_List.HorizontalViewHolder holder, final int position) {

        try {

//            holder.service_name.setText(salon_service_time_arr[position]+" min ");
            holder.service_price.setText("₹. "+salon_service_price_arr[position]);
//            holder.violation_type_new.setText(violation_type1[position]+" દંડ - " +fine_rate1[position]);
            holder.salon_service_type_new.setText(Html.fromHtml(salon_service_type_arr[position]));

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

//        holder.salon_service_type_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    Toast.makeText(context, "select item  : " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                    listener.onItemClick(holder.getAdapterPosition(),true);
////                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),true);
//
//                }
//                else
//                {
//                    Toast.makeText(context, "unchecked: " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//                    listener.onItemClick(holder.getAdapterPosition(),false);
////                    listener.onItemClick(holder.getAbsoluteAdapterPosition(),false);
//                }
//            }
//        });

    }



    @Override
    public int getItemCount() {
        return salon_service_type_arr.length;
    }

    public  class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView salon_service_id,salon_service_type_new,service_price;
        //        CheckBox salon_service_type_new;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
//            salon_service_type_new= (CheckBox) itemView.findViewById (R.id.salon_service_type_new);
            salon_service_type_new= (TextView) itemView.findViewById (R.id.salon_service_type_new);
            service_price= (TextView) itemView.findViewById (R.id.service_price);

        }


//        public Integer getAbsoluteAdapterPosition() {
//            return null;
//        }
    }
}
