package com.android.tusk.Admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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

public class CreatedTaskAdapter extends RecyclerView.Adapter<CreatedTaskAdapter.NewTaskViewholder> {

    Context context;
    List<Task> taskList;

    public interface cardActionClick{
        void onCardActionClick(int position);
    }

    cardActionClick actionClick;

    public void setOnCardActionClickListener(cardActionClick actionClick){
        this.actionClick = actionClick;
    }

    public CreatedTaskAdapter(Context context, List<Task> taskList){
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewTaskViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.admin_task_card_view, parent, false);
        return new NewTaskViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewTaskViewholder holder, int position) {
        Task task = taskList.get(position);
        List<Milestone> milestoneList = new ArrayList<>();

        for (Milestone milestone : task.getMilestones()){
            milestoneList.add(milestone);
        }

        String datestr = dateFormate(task.getCreatedAt(), 1);

        holder.heading.setText(task.getHeading());
        holder.assignedby.setText("Assigned by "+task.getAssignedBy());
        holder.milestones.setText(milestoneList.size()+" Milestones");
        holder.date.setText(datestr);
    }

    private String dateFormate(String createdAt, int i) {
        Date date;
        String dateformat;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(createdAt);
            if (i == 1){
                dateformat = new SimpleDateFormat("dd MMM , yyyy", Locale.US).format(date);
                return dateformat;
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

    public class NewTaskViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView milestones, heading, assignedby, date;
//        TextView editbtn, deletebtn;
        ImageView popup_ic;

        public NewTaskViewholder(@NonNull View itemView) {
            super(itemView);

            milestones = itemView.findViewById(R.id.admin_milestones_textview);
            heading = itemView.findViewById(R.id.admin_heading_textview);
            assignedby = itemView.findViewById(R.id.admin_assignedby_textview);
            date = itemView.findViewById(R.id.admin_date_textview);
            popup_ic = itemView.findViewById(R.id.popup_icon_view);

            popup_ic.setOnClickListener(this);
//            editbtn = itemView.findViewById(R.id.admin_edit_button);
//            deletebtn = itemView.findViewById(R.id.admin_delete_button);

//            deletebtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    actionClick.onCardActionClick(getAdapterPosition());
//                    taskList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    notifyItemRangeChanged(getAdapterPosition(), taskList.size());
//                }
//            });

        }

        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.admin_dash_popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.edit_action:
                    Toast.makeText(context, "edit clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.delete_action:
                    actionClick.onCardActionClick(getAdapterPosition());
                    taskList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), taskList.size());
                    break;
            }
            return true;
        }
    }
}
