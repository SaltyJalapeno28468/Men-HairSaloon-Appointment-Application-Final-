package com.example.e_hairsalon;

public class SalonServiceLVModel {

    private String salon_service_record_id;
    private String user_id;
    private String service_name,service_time,service_price,service_discount,img_url;
    private String created_date;


    public SalonServiceLVModel(String salon_service_record_id, String user_id, String service_name, String service_time, String service_price, String service_discount, String img_url, String created_date) {
        this.salon_service_record_id = salon_service_record_id;
        this.user_id = user_id;
        this.service_name = service_name;
        this.service_time = service_time;
        this.service_price = service_price;
        this.service_discount = service_discount;
        this.img_url = img_url;
        this.created_date = created_date;
    }

    public String getSalon_service_record_id() {
        return salon_service_record_id;
    }

    public void setSalon_service_record_id(String salon_service_record_id) {
        this.salon_service_record_id = salon_service_record_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getService_price() {
        return service_price;
    }

    public void setService_price(String service_price) {
        this.service_price = service_price;
    }

    public String getService_discount() {
        return service_discount;
    }

    public void setService_discount(String service_discount) {
        this.service_discount = service_discount;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
