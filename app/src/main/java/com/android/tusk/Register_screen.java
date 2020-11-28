package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register_screen extends AppCompatActivity {

    TextInputEditText firstedt, lastedt, branchedt, idedt, passwordedt;
    TextInputLayout first_input_lay, last_input_lay, branch_input_lay, id_input_lay, password_input_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        intializationView();

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

    }
}