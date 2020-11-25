package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login_screen extends AppCompatActivity {

    RelativeLayout submitbtn;
    TextView signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        initializedViews();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_screen.this, Register_screen.class));
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_screen.this, Dashboard.class));
            }
        });

    }

    private void initializedViews() {
        submitbtn = findViewById(R.id.submit_button);
        signupbtn = findViewById(R.id.signup_textview);
    }
}