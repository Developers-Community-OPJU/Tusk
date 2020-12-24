package com.android.tusk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.tusk.adapter.NewTaskAdapter;
import com.android.tusk.adapter.SearchAllTaskAdapter;
import com.android.tusk.fragment.Home;
import com.android.tusk.model.AllTask;
import com.android.tusk.model.Milestone;
import com.android.tusk.model.Task;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.tusk.fragment.Home.ASSIGNED_BY;
import static com.android.tusk.fragment.Home.CREATED_DATE;
import static com.android.tusk.fragment.Home.DESCRIPTION;
import static com.android.tusk.fragment.Home.HEADING;
import static com.android.tusk.fragment.Home.MILESTONES_COUNT;
import static com.android.tusk.fragment.Home.MILESTONE_LIST;

public class SearchTask extends AppCompatActivity implements SearchAllTaskAdapter.taskDetailedView {

    Toolbar toolbar;
    TextInputEditText inputEditText1;
    RecyclerView recyclerView;
    List<Task> taskArrayList;
    List<Task> taskList;
    SearchAllTaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_task);

        toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        inputEditText1 = findViewById(R.id.search_name_edittext);
        inputEditText1.requestFocus();
        if (inputEditText1.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        recyclerView = findViewById(R.id.search_task_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchAllTaskforSearch();

        searchCalendarIconClickListener();

        inputEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    }

    private void filter(String s) {
        taskList = new ArrayList<>();

        for (Task task : taskArrayList){
            String date = dateFormate(task.getCreatedAt(), 1);
            if (task.getAssignedBy().toLowerCase().contains(s.toLowerCase()) || date.contains(s)){
                taskList.add(task);
            }
        }

        taskAdapter.filteredList(taskList);
    }

    private String dateFormate(String createdAt, int i) {
        Date date;
        String dateformat;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(createdAt);
            if (i == 1){
                dateformat = new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date);
                return dateformat;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return createdAt;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void searchCalendarIconClickListener() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        final long today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select assigned Date");
        builder.setSelection(today);
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        inputEditText1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (inputEditText1.getRight() - inputEditText1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                        return true;
                    }
                }
                return false;
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                inputEditText1.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    private void fetchAllTaskforSearch() {
        Call<AllTask> allTaskCall = APIclient.getInterface().getAllTask();
        allTaskCall.enqueue(new Callback<AllTask>() {
            @Override
            public void onResponse(Call<AllTask> call, Response<AllTask> response) {
                if (response.isSuccessful()){
                    AllTask allTask = response.body();
                    taskArrayList = new ArrayList<>();

                    for (Task task: allTask.getTasks()){
                        taskArrayList.add(task);
                    }

                    taskAdapter = new SearchAllTaskAdapter(SearchTask.this, taskArrayList);
                    taskAdapter.setOnItemViewClickListener(SearchTask.this);
                    recyclerView.setAdapter(taskAdapter);
                }
            }

            @Override
            public void onFailure(Call<AllTask> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewClick(int position) {
        String searchtxt = inputEditText1.getText().toString();
        Task task;
        if (TextUtils.isEmpty(searchtxt)) {
            task = taskArrayList.get(position);
        }else {
            task = taskList.get(position);
        }
        ArrayList<Milestone> milestoneList = new ArrayList<>();

        //init model
        Milestone milestones = new Milestone();
        for (Milestone milestone : task.getMilestones()){
            milestoneList.add(milestone);
        }

        Intent intent = new Intent(this, detailed_view.class);
        intent.putExtra(HEADING, task.getHeading());
        intent.putExtra(ASSIGNED_BY, task.getAssignedBy());
        intent.putExtra(DESCRIPTION, task.getDescription());
        intent.putExtra(CREATED_DATE, task.getCreatedAt());
        intent.putExtra(MILESTONES_COUNT, milestoneList.size());
        intent.putParcelableArrayListExtra(MILESTONE_LIST, milestoneList);

        startActivity(intent);
    }
}