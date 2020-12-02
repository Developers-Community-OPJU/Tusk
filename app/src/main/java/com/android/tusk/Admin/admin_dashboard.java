package com.android.tusk.Admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.tusk.Admin.fragment.admin_dash_fragment;
import com.android.tusk.R;

public class admin_dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        initializeView();

        setActionbarOrToggle();

        getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame_container, new admin_dash_fragment()).commit();
    }

    private void setActionbarOrToggle() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setCustomView(R.layout.admin_actionbar);
        getSupportActionBar().setTitle("");
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initializeView() {
        drawerLayout = findViewById(R.id.admin_drawer_layout);
        toolbar = findViewById(R.id.admin_dashboard_toolbar);
        frameLayout = findViewById(R.id.admin_frame_container);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}