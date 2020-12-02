package com.android.tusk.Admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.tusk.R;
import com.android.tusk.adapter.NewTaskAdapter;
import com.android.tusk.model.AllTask;
import com.android.tusk.model.Task;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class admin_dash_fragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

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
                getFragmentManager().beginTransaction().replace(R.id.admin_frame_container, new create_task_fragment()).addToBackStack("dashboard").commit();
            }
        });

        return view;
    }

    private void setRecentTaskCardData() {
        Call<AllTask> allTaskCall = APIclient.getInterface().getAllTask();
        allTaskCall.enqueue(new Callback<AllTask>() {
            @Override
            public void onResponse(Call<AllTask> call, Response<AllTask> response) {
                if (response.isSuccessful()){
                    AllTask allTask = response.body();
                    List<Task> taskList = new ArrayList<>();

                    for (Task task : allTask.getTasks()){
                        taskList.add(task);
                    }

                    NewTaskAdapter taskAdapter = new NewTaskAdapter(getContext(), taskList);
                    recyclerView.setAdapter(taskAdapter);
                    taskAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<AllTask> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializeView(View view) {
        recyclerView = view.findViewById(R.id.fragment_recentTask_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        floatingActionButton = view.findViewById(R.id.createTask_floating_button);
    }

    private void ToastMassage(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}