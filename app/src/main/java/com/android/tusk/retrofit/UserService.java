package com.android.tusk.retrofit;

import com.android.tusk.model.AllTask;
import com.android.tusk.Admin.model.AssignTaskRequest;
import com.android.tusk.Admin.model.AssignTaskResponse;
import com.android.tusk.model.HeaderResponse;
import com.android.tusk.model.LoginRequest;
import com.android.tusk.model.LoginResponse;
import com.android.tusk.model.RegisterRequest;
import com.android.tusk.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    //register
    @POST("auth/register")
    Call<RegisterResponse> getRegisterResponse(@Body RegisterRequest registerRequest);

    //login
    @POST("auth/login")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);

    //all Task Data
    @GET("task/find/all")
    Call<AllTask> getAllTask();

    //create task
    @POST("task/push")
    Call<AssignTaskResponse> getCreateTaskResponse(@Body AssignTaskRequest assignTaskRequest);

    //delete task
    @DELETE("task/pull/{id}")
    Call<Void> deleteTask(@Path("id") String id);

    //passing headers for decode token
    @GET("auth/getCurrentUser")
    Call<HeaderResponse> getDecodedUserToken(@Header("x-auth-token") String token);

}
