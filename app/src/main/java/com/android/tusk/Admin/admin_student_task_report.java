package com.android.tusk.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.tusk.Admin.adapter.studentReportAdapter;
import com.android.tusk.Admin.model.User;
import com.android.tusk.Admin.model.UserList;
import com.android.tusk.R;
import com.android.tusk.retrofit.APIclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<UserList> userListCall = APIclient.getInterface().getUserList();
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()){
                    UserList body = response.body();
                    List<User> userList = new ArrayList<>();

                    for (User user : body.getUser()){
                        if (user.getUserRole().equals("Student")){
                            userList.add(user);
                        }
                    }
                    studentReportAdapter adapter = new studentReportAdapter(getApplicationContext(), userList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "sorry request failed! try again.", Toast.LENGTH_SHORT).show();
            }
        });
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