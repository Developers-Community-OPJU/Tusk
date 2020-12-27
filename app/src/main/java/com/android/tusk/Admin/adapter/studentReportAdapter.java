package com.android.tusk.Admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tusk.Admin.model.User;
import com.android.tusk.Admin.model.UserList;
import com.android.tusk.R;

import java.util.List;

public class studentReportAdapter extends RecyclerView.Adapter<studentReportAdapter.studentReportViewholder> {

    Context context;
    List<User> stringList;

    public studentReportAdapter(Context context, List<User> stringList){
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public studentReportViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_reportlist_view, parent, false);
        return new studentReportViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studentReportViewholder holder, int position) {
        User studentsList = stringList.get(position);

        holder.rollno.setText(studentsList.getRID());
        holder.name.setText(studentsList.getFirstName()+" "+studentsList.getLastName());
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class studentReportViewholder extends RecyclerView.ViewHolder{

        TextView rollno, name, viewbtn;

        public studentReportViewholder(@NonNull View itemView) {
            super(itemView);

            rollno = itemView.findViewById(R.id.rollno_textview);
            name = itemView.findViewById(R.id.student_name_textview);
            viewbtn = itemView.findViewById(R.id.view_report_button);

        }
    }
}
