package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.tusk.model.RegisterRequest;
import com.android.tusk.model.RegisterResponse;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_screen extends AppCompatActivity {

    TextInputEditText firstedt, lastedt, branchedt, idedt, passwordedt;
    TextInputLayout first_input_lay, last_input_lay, branch_input_lay, id_input_lay, password_input_lay;
    RelativeLayout registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        intializationView();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postUserInfo();
            }
        });

    }

    private void postUserInfo() {

        String firstname = firstedt.getText().toString().trim();
        String lastname = lastedt.getText().toString().trim();
        String branch = branchedt.getText().toString().trim();
        String id = idedt.getText().toString().trim();
        String password = passwordedt.getText().toString().trim();

        RegisterRequest registerRequest = new RegisterRequest(firstname, lastname, branch, id, password);

        Call<RegisterResponse> registerResponseCall = APIclient.getInterface().getRegisterResponse(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    RegisterResponse registerResponse = response.body();
                    ToastMassage(registerResponse.getMsg());

                    Intent intent = new Intent(Register_screen.this, Login_screen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                ToastMassage("failed");
            }
        });

    }

    private void ToastMassage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void intializationView() {

        //initialize editText
        firstedt = findViewById(R.id.firstname_edittext);
        lastedt = findViewById(R.id.lastname_edittext);
        branchedt = findViewById(R.id.branch_edittext);
        idedt = findViewById(R.id.register_id_edittext);
        passwordedt = findViewById(R.id.register_password_edittext);

        //initialize input Text Layout
        first_input_lay = findViewById(R.id.firstname_textInputLayout);
        last_input_lay = findViewById(R.id.lastname_textInputLayout);
        branch_input_lay = findViewById(R.id.branch_textInputLayout);
        id_input_lay = findViewById(R.id.register_id_textInputLayout);
        password_input_lay = findViewById(R.id.register_password_textInputLayout);

        //initialize submit Button
        registerbtn = findViewById(R.id.register_button);

    }
}