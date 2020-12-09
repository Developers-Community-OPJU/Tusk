package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.android.tusk.retrofit.SessionManager;

public class splash_hold extends AppCompatActivity {

    CardView startbtn;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_hold);

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

        startbtn = findViewById(R.id.getStarted_button);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(splash_hold.this, Login_screen.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sessionManager.getAuthToken() != null){
            startActivity(new Intent(splash_hold.this, Dashboard.class));
            finish();
        }
    }
}