package com.example.e_hairsalon;

public class UserAppointmentLVModel {

    private String appointment_record_id;
    private String user_id;
    private String appointment_book_date,appointment_time,total_amount,owner_txt,appointment_status;
    private String created_date;


    public UserAppointmentLVModel(String appointment_record_id, String user_id, String appointment_book_date, String appointment_time, String total_amount, String owner_txt, String appointment_status, String created_date) {
        this.appointment_record_id = appointment_record_id;
        this.user_id = user_id;
        this.appointment_book_date = appointment_book_date;
        this.appointment_time = appointment_time;
        this.total_amount = total_amount;
        this.owner_txt = owner_txt;
        this.appointment_status = appointment_status;
        this.created_date = created_date;
    }


    public String getAppointment_record_id() {
        return appointment_record_id;
    }

    public void setAppointment_record_id(String appointment_record_id) {
        this.appointment_record_id = appointment_record_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAppointment_book_date() {
        return appointment_book_date;
    }

    public void setAppointment_book_date(String appointment_book_date) {
        this.appointment_book_date = appointment_book_date;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getOwner_txt() {
        return owner_txt;
    }

    public void setOwner_txt(String owner_txt) {
        this.owner_txt = owner_txt;
    }

    public String getAppointment_status() {
        return appointment_status;
    }

    public void setAppointment_status(String appointment_status) {
        this.appointment_status = appointment_status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
