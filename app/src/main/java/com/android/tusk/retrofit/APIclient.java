package com.android.tusk.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIclient {
    public static final String BASE_URL = "https://tusk-server-api.herokuapp.com";
    public static final String Append = "/api/";

    public static UserService userService = null;

    public static UserService getInterface(){
        if (userService == null){

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL+Append)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
            userService = retrofit.create(UserService.class);
        }
        return userService;
    }
}
