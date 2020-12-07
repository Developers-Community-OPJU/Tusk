package com.android.tusk.retrofit;

import com.android.tusk.model.AllTask;
import com.android.tusk.model.AssignTaskRequest;
import com.android.tusk.model.AssignTaskResponse;
import com.android.tusk.model.LoginRequest;
import com.android.tusk.model.LoginResponse;
import com.android.tusk.model.RegisterRequest;
import com.android.tusk.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    //register
    @POST("auth/register")
    Call<RegisterResponse> getRegisterResponse(@Body RegisterRequest registerRequest);

    //login
    @POST("auth/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    //new Task Data
    @GET("task/find/all")
    Call<AllTask> getAllTask();

    //create task
    @POST("task/push")
    Call<AssignTaskResponse> getCreateTaskResponse(@Body AssignTaskRequest assignTaskRequest);
}
