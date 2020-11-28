package com.android.tusk.retrofit;

import com.android.tusk.model.RegisterRequest;
import com.android.tusk.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    //register
    @POST("/auth/register")
    Call<RegisterResponse> getRegisterResponse(@Body RegisterRequest registerRequest);

}
