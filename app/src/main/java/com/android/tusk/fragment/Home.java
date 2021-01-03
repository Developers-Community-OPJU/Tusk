package com.android.tusk.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.tusk.Dashboard;
import com.android.tusk.R;
import com.android.tusk.adapter.NewTaskAdapter;
import com.android.tusk.detailed_view;
import com.android.tusk.model.AllTask;
import com.android.tusk.model.Milestone;
import com.android.tusk.model.Task;
import com.android.tusk.retrofit.APIclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements NewTaskAdapter.taskDetailedView {

    public static final String HEADING = "heading";
    public static final String DESCRIPTION = "description";
    public static final String ASSIGNED_BY = "assigned_by";
    public static final String MILESTONES_COUNT = "milestones";
    public static final String MILESTONE_LIST = "milestone_array_list";
    public static final String CREATED_DATE = "created_date";
    public static final String DUE_DATE = "due_date";

    RecyclerView newTaskRecycler;
    List<Task> taskArrayList;

    LinearLayout taskabilitymsg, connectionerrormsg;
    ProgressBar content_progress;
    NestedScrollView content;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeView(view);
        fetchNewTaskdate();

        return view;
    }

    private void fetchNewTaskdate() {
        content_progress.setVisibility(View.VISIBLE);
        Call<AllTask> allTaskCall = APIclient.getInterface().getAllTask();
        allTaskCall.enqueue(new Callback<AllTask>() {
            @Override
            public void onResponse(Call<AllTask> call, Response<AllTask> response) {
                if (response.isSuccessful()) {
                    AllTask allTask = response.body();
                    taskArrayList = new ArrayList<>();

                    Date date = Calendar.getInstance().getTime();
                    String currentdate = String.valueOf(date);

                    for (Task task : allTask.getTasks()) {
                        if (dateFormate(task.getCreatedAt(), 1).equals(dateFormate(currentdate, 2))) {
                            taskArrayList.add(task);
                        }
                    }
                    if (!taskArrayList.isEmpty()) {
                        newTaskRecycler.setVisibility(View.VISIBLE);
                        taskabilitymsg.setVisibility(View.GONE);
                        NewTaskAdapter taskAdapter = new NewTaskAdapter(getContext(), taskArrayList);
                        taskAdapter.setOnItemViewClickListener(Home.this);
                        newTaskRecycler.setAdapter(taskAdapter);
                    } else {
                        newTaskRecycler.setVisibility(View.GONE);
                        taskabilitymsg.setVisibility(View.VISIBLE);
                    }
                    content_progress.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllTask> call, Throwable t) {
                ToastMassage("failed");
                content_progress.setVisibility(View.GONE);
                connectionerrormsg.setVisibility(View.VISIBLE);
            }
        });
    }

    private String dateFormate(String rawdate, int i) {
        Date date = null;
        String format;
        try {
            if (i == 1) {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(rawdate);
                format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
                return format;
            }
            if (i == 2) {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault()).parse(rawdate);
                format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
                return format;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rawdate;
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializeView(View view) {
        newTaskRecycler = view.findViewById(R.id.fragment_newTask_recyclerView);
        newTaskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newTaskRecycler.setHasFixedSize(false);

        taskabilitymsg = view.findViewById(R.id.task_ability_massage);
        content_progress = view.findViewById(R.id.circulre_progress);
        content = view.findViewById(R.id.home_content);
        connectionerrormsg = view.findViewById(R.id.connection_error_msg);
    }

    @Override
    public void onViewClick(int position) {
        Task task = taskArrayList.get(position);
        ArrayList<Milestone> milestoneList = new ArrayList<>();

        //init model
        Milestone milestones = new Milestone();
        for (Milestone milestone : task.getMilestones()) {
            milestoneList.add(milestone);
        }

        Intent intent = new Intent(getContext(), detailed_view.class);
        intent.putExtra(HEADING, task.getHeading());
        intent.putExtra(ASSIGNED_BY, task.getAssignedBy());
        intent.putExtra(DESCRIPTION, task.getDescription());
        intent.putExtra(CREATED_DATE, task.getCreatedAt());
        intent.putExtra(DUE_DATE, task.getDueDate());
        intent.putExtra(MILESTONES_COUNT, milestoneList.size());
        intent.putParcelableArrayListExtra(MILESTONE_LIST, milestoneList);

        startActivity(intent);
    }
}