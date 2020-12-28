package com.android.tusk.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.tusk.Admin.adapter.assigningTaskAdapter;
import com.android.tusk.Admin.model.User;
import com.android.tusk.Admin.model.UserList;
import com.android.tusk.R;
import com.android.tusk.retrofit.APIclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class assigning_task extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    AutoCompleteTextView branchatxt, semesteratxt;

    ArrayList<String> branchArrayList;
    ArrayAdapter<String> branchArrayAdapter;

    ArrayList<String> semArrayList;
    ArrayAdapter<String> semArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_assigning_task);

        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setCustomView(R.layout.admin_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        setDropdownMenu();

        getDataFromServer();

    }

    private void getDataFromServer() {
        Call<UserList> userListCall = APIclient.getInterface().getUserList();
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()){
                    UserList userdata = response.body();
                    List<User> userList = new ArrayList<>();

                    for (User user : userdata.getUser()){
                        if (user.getUserRole().equals("Student")){
                            userList.add(user);
                        }
                    }

                    assigningTaskAdapter adapter = new assigningTaskAdapter(getApplicationContext(), userList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed, Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
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
        branchatxt.setAdapter(branchArrayAdapter);

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
        semesteratxt.setAdapter(semArrayAdapter);
    }

    private void initView() {
        toolbar = findViewById(R.id.admin_dashboard_toolbar);

        recyclerView = findViewById(R.id.student_assign_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        branchatxt = findViewById(R.id.branch_search_autocomplete_textview);
        semesteratxt = findViewById(R.id.semester_search_autocomplete_textview);
    }
}