package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tusk.Admin.admin_dashboard;
import com.android.tusk.model.Decoded;
import com.android.tusk.model.HeaderResponse;
import com.android.tusk.model.LoginRequest;
import com.android.tusk.model.LoginResponse;
import com.android.tusk.retrofit.APIclient;
import com.android.tusk.retrofit.SessionManager;
import com.android.tusk.utils.ProgressDialog;
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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initializedViews();

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

        progressDialog = new ProgressDialog(this);

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
                progressDialog.showLoader();
                requestToServer();
            }
        });

    }

    private void requestToServer() {
        String id = idedtx.getText().toString().trim();
        String password = passwordedtx.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest(id, password);

        Call<LoginResponse> loginResponseCall = APIclient.getInterface().getLoginResponse(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    final LoginResponse loginResponse = response.body();

                    if (loginResponse.getAllowed() && loginResponse.getToken() != null) {

                        sessionManager.saveAuthToken(loginResponse.getToken());

                        Call<HeaderResponse> headerResponseCall = APIclient.getInterface().getDecodedUserToken(loginResponse.getToken());
                        headerResponseCall.enqueue(new Callback<HeaderResponse>() {
                            @Override
                            public void onResponse(Call<HeaderResponse> call, Response<HeaderResponse> response) {
                                if (response.isSuccessful()) {
                                    HeaderResponse headerResponse = response.body();
                                    Decoded decoded = headerResponse.getDecoded();
                                    if (headerResponse.getAllowed()) {

                                        sessionManager.saveUserInfo(
                                                decoded.getId(),
                                                decoded.getFirstName(),
                                                decoded.getLastName(),
                                                decoded.getBranch(),
                                                decoded.getRID(),
                                                decoded.getUserRole()
                                        );

                                        if (decoded.getUserRole().equals("Student")) {
                                            ToastMassage(loginResponse.getMsg());
                                            progressDialog.dismissLoader();
                                            Intent intent = new Intent(Login_screen.this, Dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (decoded.getUserRole().equals("Admin")) {
                                            ToastMassage(loginResponse.getMsg());
                                            progressDialog.dismissLoader();
                                            Intent intent = new Intent(Login_screen.this, admin_dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<HeaderResponse> call, Throwable t) {
                                ToastMassage("auth error please try again");
                                progressDialog.dismissLoader();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ToastMassage("failed");
                progressDialog.dismissLoader();
            }
        });
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
}