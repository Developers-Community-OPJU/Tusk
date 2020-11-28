package com.android.tusk.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.tusk.R;

public class SessionManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String USER_TOKEN = "user_token";

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

    public String getAuthToken(){
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void removeToken(){
        editor = sharedPreferences.edit();
        editor.remove(USER_TOKEN);
        editor.apply();
    }

}
