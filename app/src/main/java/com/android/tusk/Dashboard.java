package com.android.tusk;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.tusk.fragment.Home;
import com.android.tusk.retrofit.SessionManager;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RelativeLayout primary, logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        initializationView();

        sessionManager = new SessionManager(this);
        sessionManager.CreatePreferences();

        setDrawer();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Home()).commit();

    }

    private void setDrawer() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initializationView() {
        toolbar = findViewById(R.id.dashboard_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        primary = this.findViewById(R.id.primary_action);
        primary.setOnClickListener(this);
        logout = this.findViewById(R.id.logout_action);
        logout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.primary_action:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Home()).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logout_action:
                sessionManager.removeToken();
                startActivity(new Intent(Dashboard.this, Login_screen.class));
                finish();
                break;
        }
    }
}