package com.android.tusk.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.tusk.R;

import java.io.File;

public class SessionManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String USER_TOKEN = "user_token";
    public static final String UID = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String BRANCH = "branch";
    public static final String RID = "registration_id";
    public static final String USER_ROLE = "user_role";

    public SessionManager(Context context){
        this.context = context;
    }

    public void CreatePreferences(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAuthToken(String token){
        editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public void saveUserInfo(String user_id, String firstName, String lastName, String branch, String rid, String user_role){
        editor = sharedPreferences.edit();
        editor.putString(UID, user_id);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(BRANCH, branch);
        editor.putString(RID, rid);
        editor.putString(USER_ROLE, user_role);
        editor.apply();
    }

    public String getUID() {
        return sharedPreferences.getString(UID, null);
    }

    public String getFirstName() {
        return sharedPreferences.getString(FIRST_NAME, null);
    }

    public String getLastName() {
        return sharedPreferences.getString(LAST_NAME, null);
    }

    public String getBRANCH() {
        return sharedPreferences.getString(BRANCH, null);
    }

    public String getRID() {
        return sharedPreferences.getString(RID, null);
    }

    public String getUserRole(){
        return sharedPreferences.getString(USER_ROLE, null);
    }

    public String getAuthToken(){
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void removeToken(){
        editor = sharedPreferences.edit();
        editor.remove(USER_TOKEN);
        editor.remove(UID);
        editor.remove(FIRST_NAME);
        editor.remove(LAST_NAME);
        editor.remove(BRANCH);
        editor.remove(RID);
        editor.remove(USER_ROLE);
        editor.apply();
    }

}
