package com.android.tusk.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.tusk.Admin.adapter.studentReportAdapter;
import com.android.tusk.Admin.model.StudentsList;
import com.android.tusk.R;

import java.util.ArrayList;
import java.util.List;

public class admin_student_task_report extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    AutoCompleteTextView branch, semester;

    ArrayList<String> branchArrayList;
    ArrayAdapter<String> branchArrayAdapter;

    ArrayList<String> semArrayList;
    ArrayAdapter<String> semArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student_task_report);

        initViews();

        getStudentDataFromServer();

        setDropdownMenu();

    }

    private void setDropdownMenu() {

        //branch dropdown menu
        branchArrayList = new ArrayList<>();
        branchArrayList.add("All");
        branchArrayList.add("CSE");
        branchArrayList.add("EEE");
        branchArrayList.add("MECH");
        branchArrayList.add("META");
        branchArrayList.add("CIVIL");

        branchArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_entity, branchArrayList);
        branch.setAdapter(branchArrayAdapter);

        //semester dropdown menu
        semArrayList = new ArrayList<>();
        semArrayList.add("All");
        semArrayList.add("1 semester");
        semArrayList.add("2 semester");
        semArrayList.add("3 semester");
        semArrayList.add("4 semester");
        semArrayList.add("5 semester");
        semArrayList.add("6 semester");
        semArrayList.add("7 semester");
        semArrayList.add("8 semester");

        semArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_entity, semArrayList);
        semester.setAdapter(semArrayAdapter);
    }

    private void getStudentDataFromServer() {
        List<StudentsList> studentsLists = new ArrayList<>();
        studentsLists.add(new StudentsList("01UG18020029", "Roshan nahak"));
        studentsLists.add(new StudentsList("01UG18020030", "Samridhi mohanty"));
        studentsLists.add(new StudentsList("01UG18020021", "Roshan nahak"));
        studentsLists.add(new StudentsList("01UG18020005", "Aman Vishwakarma"));
        studentsLists.add(new StudentsList("01UG18020049", "sohel kumar"));

        studentReportAdapter adapter = new studentReportAdapter(this, studentsLists);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        toolbar = findViewById(R.id.admin_dashboard_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.student_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        branch = findViewById(R.id.branch_autocomplete_textview);
        semester = findViewById(R.id.semester_autocomplete_textview);
    }
}