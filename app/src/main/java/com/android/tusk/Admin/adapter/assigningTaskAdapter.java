package com.android.tusk.Admin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tusk.Admin.model.User;
import com.android.tusk.R;

import java.util.ConcurrentModificationException;
import java.util.List;

public class assigningTaskAdapter extends RecyclerView.Adapter<assigningTaskAdapter.assignTaskViewHolder> {

    Context context;
    List<User> userList;

    public assigningTaskAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public assignTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.assign_student_view, parent, false);
        return new assignTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull assignTaskViewHolder holder, final int position) {
        final User user = userList.get(position);

        holder.name.setText(user.getFirstName()+" "+user.getLastName());

        holder.checkBox.setChecked(user.getSelected());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class assignTaskViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CheckBox checkBox;
        LinearLayout linearLayout;

        public assignTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.assign_student_name_textview);
            checkBox = itemView.findViewById(R.id.student_checkbox);
            linearLayout = itemView.findViewById(R.id.linearlayout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = userList.get(getAdapterPosition());
                    user.setSelected(!user.getSelected());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
