package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tusk.Admin.admin_dashboard;
import com.android.tusk.model.LoginRequest;
import com.android.tusk.model.LoginResponse;
import com.android.tusk.retrofit.APIclient;
import com.android.tusk.retrofit.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_screen extends AppCompatActivity {

    RelativeLayout submitbtn;
    TextView signupbtn;
    TextInputLayout id_textInput_lay, password_textInput_lay;
    TextInputEditText idedtx, passwordedtx;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initializedViews();

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_screen.this, Register_screen.class));
            }
        });

        //submit Button
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestToServer();
            }
        });

    }

    private void requestToServer() {
        String id = idedtx.getText().toString().trim();
        String password = passwordedtx.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest(id, password);

        //admin login check
        if (id.equals("123456") && password.equals("admin123")) {
            startActivity(new Intent(Login_screen.this, admin_dashboard.class));
        } else {
            Call<LoginResponse> loginResponseCall = APIclient.getInterface().getLoginResponse(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse loginResponse = response.body();
                        ToastMassage(loginResponse.getMsg());

                        if (loginResponse.getAllowed()) {

                            sessionManager.saveAuthToken(loginResponse.getToken());

                            Intent intent = new Intent(Login_screen.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    ToastMassage("failed");
                }
            });
        }
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializedViews() {

        //initialize button
        submitbtn = findViewById(R.id.submit_button);
        signupbtn = findViewById(R.id.signup_textview);

        //initialize textInputLayout
        id_textInput_lay = findViewById(R.id.login_id_textInputLayout);
        password_textInput_lay = findViewById(R.id.login_password_textInputLayout);

        //initialize textInputEditText
        idedtx = findViewById(R.id.login_id_edittext);
        passwordedtx = findViewById(R.id.login_password_edittext);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}