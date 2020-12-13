package com.android.tusk.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.tusk.Dashboard;
import com.android.tusk.R;
import com.android.tusk.adapter.NewTaskAdapter;
import com.android.tusk.model.AllTask;
import com.android.tusk.model.Milestone;
import com.android.tusk.model.Task;
import com.android.tusk.retrofit.APIclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements NewTaskAdapter.taskDetailedView {

    public static final String HEADING = "heading";
    public static final String DESCRIPTION = "description";
    public static final String ASSIGNED_BY = "assigned_by";
    public static final String MILESTONES = "milestones";
    public static final String CREATED_DATE = "created_date";

    RecyclerView newTaskRecycler;
    List<Task> taskArrayList;

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

                    NewTaskAdapter taskAdapter = new NewTaskAdapter(getContext(), taskArrayList);
                    taskAdapter.setOnItemViewClickListener(Home.this);
                    newTaskRecycler.setAdapter(taskAdapter);
                }
            }

            @Override
            public void onFailure(Call<AllTask> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initializeView(View view) {
        newTaskRecycler = view.findViewById(R.id.fragment_newTask_recyclerView);
        newTaskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newTaskRecycler.setHasFixedSize(true);
    }

    @Override
    public void onViewClick(int position) {
        Task task = taskArrayList.get(position);
        List<Milestone> milestoneList = new ArrayList<>();

        for (Milestone milestone : task.getMilestones()){
            milestoneList.add(milestone);
        }

        detailed_view viewFragment = new detailed_view();
        Bundle bundle = new Bundle();
        bundle.putString(HEADING, task.getHeading());
        bundle.putString(ASSIGNED_BY, task.getAssignedBy());
        bundle.putString(DESCRIPTION, task.getDescription());
        bundle.putString(CREATED_DATE, task.getCreatedAt());
        bundle.putInt(MILESTONES, milestoneList.size());
        viewFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_container, viewFragment).addToBackStack("home").commit();
    }
}