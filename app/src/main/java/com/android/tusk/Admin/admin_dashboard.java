package com.android.tusk.Admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.android.tusk.Admin.fragment.admin_dash_fragment;
import com.android.tusk.R;

public class admin_dashboard extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    RelativeLayout dashboard, studentreport;

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

        dashboard = findViewById(R.id.dashboard_drawer_action);
        dashboard.setOnClickListener(this);
        studentreport = findViewById(R.id.student_report_drawer_action);
        studentreport.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    //drawer action clicks
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dashboard_drawer_action:
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame_container, new admin_dash_fragment()).commit();
                break;
            case R.id.student_report_drawer_action:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(admin_dashboard.this, admin_student_task_report.class);
                startActivity(intent);
                break;
        }
    }
}