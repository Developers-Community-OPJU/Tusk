package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.tusk.model.RegisterRequest;
import com.android.tusk.model.RegisterResponse;
import com.android.tusk.retrofit.APIclient;
import com.android.tusk.utils.ProgressDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_screen extends AppCompatActivity implements TextWatcher {

    TextInputEditText firstedt, lastedt, branchedt, idedt, passwordedt;
    TextInputLayout first_input_lay, last_input_lay, branch_input_lay, id_input_lay, password_input_lay;
    RelativeLayout registerbtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        intializationView();

        progressDialog = new ProgressDialog(this);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    progressDialog.showLoader();
                    postUserInfo();
                }
            }
        });

    }

    private boolean validation() {
        String firstname = firstedt.getText().toString().trim();
        //String lastname = lastedt.getText().toString().trim();
        String branch = branchedt.getText().toString().trim();
        String id = idedt.getText().toString().trim();
        String password = passwordedt.getText().toString().trim();

        //implement text change change listener
        firstedt.addTextChangedListener(this);
        branchedt.addTextChangedListener(this);
        idedt.addTextChangedListener(this);
        passwordedt.addTextChangedListener(this);

        if (TextUtils.isEmpty(firstname)) {
            first_input_lay.setErrorEnabled(true);
            first_input_lay.setError("first name is required");
            firstedt.setFocusableInTouchMode(true);
            firstedt.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(branch)) {
            branch_input_lay.setErrorEnabled(true);
            branch_input_lay.setError("branch is required");
            branchedt.setFocusableInTouchMode(true);
            branchedt.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(id)) {
            id_input_lay.setErrorEnabled(true);
            id_input_lay.setError("enter registration id");
            idedt.setFocusableInTouchMode(true);
            idedt.requestFocus();
            return false;
        }else if (id.length() < 6){
            id_input_lay.setErrorEnabled(true);
            id_input_lay.setError("length must be atleast 6 characters");
            idedt.setFocusableInTouchMode(true);
            idedt.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            password_input_lay.setErrorEnabled(true);
            password_input_lay.setError("password is required");
            passwordedt.setFocusableInTouchMode(true);
            passwordedt.requestFocus();
            return false;
        }else if (password.length() < 6){
            password_input_lay.setErrorEnabled(true);
            password_input_lay.setError("length must be atleast 6 characters");
            passwordedt.setFocusableInTouchMode(true);
            passwordedt.requestFocus();
            return false;
        }
        return true;
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
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    ToastMassage(registerResponse.getMsg());
                    progressDialog.dismissLoader();

                    Intent intent = new Intent(Register_screen.this, Login_screen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismissLoader();
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


    //text change listener methods
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (first_input_lay.isErrorEnabled()){
            first_input_lay.setErrorEnabled(false);
        }
        if (branch_input_lay.isErrorEnabled()){
            branch_input_lay.setErrorEnabled(false);
        }
        if (id_input_lay.isErrorEnabled()){
            id_input_lay.setErrorEnabled(false);
        }
        if (password_input_lay.isErrorEnabled()){
            password_input_lay.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}