package com.android.tusk.Admin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.tusk.Admin.adapter.CreatedTaskAdapter;
import com.android.tusk.Admin.create_task;
import com.android.tusk.R;
import com.android.tusk.adapter.NewTaskAdapter;
import com.android.tusk.model.AllTask;
import com.android.tusk.model.Task;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class admin_dash_fragment extends Fragment implements CreatedTaskAdapter.cardActionClick {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton floatingActionButton;
    List<Task> taskList;
    NestedScrollView content;
    LinearLayout networkErrorMsg;
    ProgressBar progressBar;

    CreatedTaskAdapter taskAdapter;

    public admin_dash_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_dash_fragment, container, false);

        initializeView(view);

        setRecentTaskCardData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), create_task.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setRecentTaskCardData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<AllTask> allTaskCall = APIclient.getInterface().getAllTask();
        allTaskCall.enqueue(new Callback<AllTask>() {
            @Override
            public void onResponse(Call<AllTask> call, Response<AllTask> response) {
                if (response.isSuccessful()){
                    AllTask allTask = response.body();
                    taskList = new ArrayList<>();

                    for (Task task : allTask.getTasks()){
                        taskList.add(task);
                    }

                    taskAdapter = new CreatedTaskAdapter(getContext(), taskList);
                    recyclerView.setAdapter(taskAdapter);
                    taskAdapter.setOnCardActionClickListener(admin_dash_fragment.this);
                    taskAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllTask> call, Throwable t) {
                ToastMassage("can't reach to the server! try again");
                progressBar.setVisibility(View.GONE);
                networkErrorMsg.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initializeView(View view) {
        recyclerView = view.findViewById(R.id.fragment_recentTask_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);

        floatingActionButton = view.findViewById(R.id.createTask_floating_button);
        content = view.findViewById(R.id.admin_home_content);
        networkErrorMsg = view.findViewById(R.id.network_error_msg_layout);
        progressBar = view.findViewById(R.id.admin_progressbar);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardActionClick(final int position) {
        //delete task
        Task task = taskList.get(position);

        String taskId = task.getId();

        Call<Void> deleteTaskCall = APIclient.getInterface().deleteTask(taskId);
        deleteTaskCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200){
                    ToastMassage("task remove successfully");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ToastMassage("failed something error!!");
            }
        });
    }
}