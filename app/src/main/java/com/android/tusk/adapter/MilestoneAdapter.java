package com.android.tusk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tusk.R;
import com.android.tusk.model.Milestone;

import java.util.ArrayList;

public class MilestoneAdapter extends RecyclerView.Adapter<MilestoneAdapter.MilestoneViewHolder> {

    Context context;
    ArrayList<Milestone> milestoneList;
    public MilestoneAdapter(Context context, ArrayList<Milestone> milestoneList){
        this.context = context;
        this.milestoneList = milestoneList;
    }
    @NonNull
    @Override
    public MilestoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.milestone_expandable_view, parent, false);
        return new MilestoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilestoneViewHolder holder, int position) {
        Milestone milestone = milestoneList.get(position);

        holder.title.setText(milestone.getTitle());
        holder.expand_title.setText(milestone.getTitle());
        holder.expand_description.setText(milestone.getDescription());

        Boolean isExpand = milestone.getIsExpanded();
        holder.expanded_layout.setVisibility(isExpand ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return milestoneList.size();
    }

    public class MilestoneViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView expand_title, expand_description;
        LinearLayout clickable_layout, expanded_layout;

        public MilestoneViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.milestone_title_textview);
            expand_title = itemView.findViewById(R.id.expand_title_textview);
            expand_description = itemView.findViewById(R.id.expand_description_textview);

            clickable_layout = itemView.findViewById(R.id.clickable_linear_layout);
            expanded_layout = itemView.findViewById(R.id.expandable_linear_layout);

            clickable_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Milestone milestone = milestoneList.get(getAdapterPosition());
                    milestone.setIsExpanded(!milestone.getIsExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
