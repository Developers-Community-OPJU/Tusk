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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchAllTaskAdapter extends RecyclerView.Adapter<SearchAllTaskAdapter.SearchViewholder> {

    Context context;
    List<Task> taskList;

    //search filter
    public void filteredList(List<Task> filteredTaskList){
        taskList = filteredTaskList;
        notifyDataSetChanged();
    }

    public interface taskDetailedView{
        void onViewClick(int position);
    }

    taskDetailedView detailedView;

    public void setOnItemViewClickListener(taskDetailedView detailedView){
        this.detailedView = detailedView;
    }

    public SearchAllTaskAdapter(Context context, List<Task> taskList){
        this.context = context;
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public SearchViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_card_view, parent, false);
        return new SearchViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewholder holder, int position) {
        Task task = taskList.get(position);
        List<Milestone> milestoneList = new ArrayList<>();

        for (Milestone milestone : task.getMilestones()){
            milestoneList.add(milestone);
        }

        String date = dateFormate(task.getCreatedAt(), 1);

        holder.heading.setText(task.getHeading());
        holder.assignedby.setText("Assigned by "+task.getAssignedBy());
        holder.milestones.setText(milestoneList.size()+" Milestones");
        holder.date.setText(date);
    }

    private String dateFormate(String createdAt, int i) {
        Date date = null;
        String dateformate;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(createdAt);
            if (i == 1){
                dateformate = new SimpleDateFormat("dd MMM , yyyy", Locale.US).format(date);
                return dateformate;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdAt;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class SearchViewholder extends RecyclerView.ViewHolder{

        TextView milestones, heading, assignedby, date;
        TextView viewbtn;

        public SearchViewholder(@NonNull View itemView) {
            super(itemView);
            milestones = itemView.findViewById(R.id.milestones_textview);
            heading = itemView.findViewById(R.id.heading_textview);
            assignedby = itemView.findViewById(R.id.assignedby_textview);
            date = itemView.findViewById(R.id.date_textview);
            viewbtn = itemView.findViewById(R.id.view_button);

            viewbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailedView.onViewClick(getAdapterPosition());
                }
            });
        }
    }
}
