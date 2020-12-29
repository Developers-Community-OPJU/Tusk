package com.android.tusk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tusk.adapter.MilestoneAdapter;
import com.android.tusk.model.Milestone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.android.tusk.fragment.Home.ASSIGNED_BY;
import static com.android.tusk.fragment.Home.CREATED_DATE;
import static com.android.tusk.fragment.Home.DESCRIPTION;
import static com.android.tusk.fragment.Home.DUE_DATE;
import static com.android.tusk.fragment.Home.HEADING;
import static com.android.tusk.fragment.Home.MILESTONES_COUNT;
import static com.android.tusk.fragment.Home.MILESTONE_LIST;

public class detailed_view extends AppCompatActivity {

    TextView heading, assigned_by, description, millstone, created_date, due_date;
    RecyclerView expand_recycler;
    ArrayList<Milestone> milestoneArrayList;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view);

        initializaView();

        setIntentData();

    }

    private void setIntentData() {
        Intent intent = getIntent();
        String get_heading = intent.getStringExtra(HEADING);
        String get_assignby = intent.getStringExtra(ASSIGNED_BY);
        String get_description = intent.getStringExtra(DESCRIPTION);
        String get_raw_date = intent.getStringExtra(CREATED_DATE);
        String get_raw_duedate = intent.getStringExtra(DUE_DATE);
        int get_millstone = intent.getIntExtra(MILESTONES_COUNT, 0);
        milestoneArrayList = intent.getParcelableArrayListExtra(MILESTONE_LIST);

        String date = dateformate(get_raw_date, 1);
        String duedate = dateformate(get_raw_duedate, 2);

        heading.setText(get_heading);
        assigned_by.setText(get_assignby);
        description.setText(get_description);
        created_date.setText(date);
        due_date.setText("Due:  "+duedate);
        millstone.setText(get_millstone+" Milestones");

        MilestoneAdapter adapter = new MilestoneAdapter(this, milestoneArrayList);
        expand_recycler.setAdapter(adapter);
    }

    private String dateformate(String get_raw_date, int i) {
        Date date = null;
        String dateFormat;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(get_raw_date);
            if (i == 1){
                dateFormat = new SimpleDateFormat("dd MMM , yyyy", Locale.US).format(date);
                return dateFormat;
            }
            if (i == 2){
                dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.US).format(date);
                return dateFormat;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return get_raw_date;
    }

    private void initializaView() {
        toolbar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        heading = findViewById(R.id.view_heading_textview);
        assigned_by = findViewById(R.id.view_assignby_textview);
        description = findViewById(R.id.view_description_textview);
        millstone = findViewById(R.id.view_millstone_textview);
        created_date = findViewById(R.id.created_date_textview);
        due_date = findViewById(R.id.duedate_textview);

        expand_recycler = findViewById(R.id.expandable_milestone_recyclerview);
        expand_recycler.setLayoutManager(new LinearLayoutManager(this));
        expand_recycler.setHasFixedSize(true);
    }
}
