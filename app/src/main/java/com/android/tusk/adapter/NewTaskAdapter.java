package com.android.tusk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tusk.R;
import com.android.tusk.model.Milestone;
import com.android.tusk.model.Task;

import java.util.ArrayList;
import java.util.List;

public class NewTaskAdapter extends RecyclerView.Adapter<NewTaskAdapter.NewTaskViewholder> {

    Context context;
    List<Task> taskList;

    public NewTaskAdapter(Context context, List<Task> taskList){
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewTaskViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_card_view, parent, false);
        return new NewTaskViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewTaskViewholder holder, int position) {
        Task task = taskList.get(position);
        List<Milestone> milestoneList = new ArrayList<>();

        for (Milestone milestone : task.getMilestones()){
            milestoneList.add(milestone);
        }

        holder.heading.setText(task.getHeading());
        holder.assignedby.setText("Assigned by "+task.getAssignedBy());
        holder.milestones.setText(milestoneList.size()+" Milestones");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class NewTaskViewholder extends RecyclerView.ViewHolder{

        TextView milestones, heading, assignedby, date;

        public NewTaskViewholder(@NonNull View itemView) {
            super(itemView);

            milestones = itemView.findViewById(R.id.milestones_textview);
            heading = itemView.findViewById(R.id.heading_textview);
            assignedby = itemView.findViewById(R.id.assignedby_textview);
            date = itemView.findViewById(R.id.date_textview);

        }
    }
}
