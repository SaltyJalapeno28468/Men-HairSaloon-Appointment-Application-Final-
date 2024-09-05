package com.example.e_hairsalon;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences=context.getSharedPreferences("AppKey",0);
        editor=sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    public void setUsername(String username){
        editor.putString("KEY_USERNAME",username);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME","");
    }

    public void setUser_ID(String police_user_id){
        editor.putString("KEY_USER_ID",police_user_id);
        editor.commit();
    }

    public String getUser_ID(){
        return sharedPreferences.getString("KEY_USER_ID","");
    }

    public void setApp_language(String language){
        editor.putString("APP_LANGUAGE",language);
        editor.commit();
    }

    public String getApp_language(){
        return sharedPreferences.getString("APP_LANGUAGE","");
    }

    public void setSite_Url(String site_url){
        editor.putString("SITE_URL",site_url);
        editor.commit();
    }

    public String getSite_Url(){
        return sharedPreferences.getString("SITE_URL","");
    }

    public void setNotification_Id(String Notification_Id){
        editor.putString("NOTIFICATION_ID",Notification_Id);
        editor.commit();
    }

    public String getNotification_Id(){
        return sharedPreferences.getString("NOTIFICATION_ID","");
    }

    public void setNotification_Token(String Notification_Token){
        editor.putString("NOTIFICATION_TOKEN",Notification_Token);
        editor.commit();
    }

    public String getNotification_Token(){
        return sharedPreferences.getString("NOTIFICATION_TOKEN","");
    }

    public void setname(String name) {
        editor.putString("KEY_USER_NAME",name);
        editor.commit();
    }
    public String getname(){
        return sharedPreferences.getString("KEY_USER_NAME","");
    }

    public void setmobile_no(String mobile_no) {
        editor.putString("KEY_mobile_no",mobile_no);
        editor.commit();
    }
    public String getmobile_no(){
        return sharedPreferences.getString("KEY_mobile_no","");
    }

    public void setemail(String email) {
        editor.putString("KEY_email",email);
        editor.commit();
    }
    public String getemail(){
        return sharedPreferences.getString("KEY_email","");
    }
}
