package com.android.tusk.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tusk.R;
import com.android.tusk.adapter.MilestoneAdapter;
import com.android.tusk.model.Milestone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.android.tusk.fragment.Home.ASSIGNED_BY;
import static com.android.tusk.fragment.Home.CREATED_DATE;
import static com.android.tusk.fragment.Home.DESCRIPTION;
import static com.android.tusk.fragment.Home.HEADING;
import static com.android.tusk.fragment.Home.MILESTONES_COUNT;
import static com.android.tusk.fragment.Home.MILESTONE_LIST;

public class detailed_view extends Fragment {

    TextView heading, assigned_by, description, millstone, created_date;
    RecyclerView expand_recycler;
    ArrayList<Milestone> milestoneArrayList;

    public detailed_view() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        initializaView(view);

        setBundleData(view);

        return view;
    }

    private void setBundleData(View view) {
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String get_heading = bundle.getString(HEADING);
            String get_assignby = bundle.getString(ASSIGNED_BY);
            String get_description = bundle.getString(DESCRIPTION);
            String get_raw_date = bundle.getString(CREATED_DATE);
            int get_millstone = bundle.getInt(MILESTONES_COUNT);
            milestoneArrayList = bundle.getParcelableArrayList(MILESTONE_LIST);

            String date = dateformate(get_raw_date, 1);

            heading.setText(get_heading);
            assigned_by.setText(get_assignby);
            description.setText(get_description);
            created_date.setText(date);
            millstone.setText(get_millstone+" Milestones");

            MilestoneAdapter adapter = new MilestoneAdapter(getContext(), milestoneArrayList);
            expand_recycler.setAdapter(adapter);
        }
    }

    private String dateformate(String get_raw_date, int i) {
        Date date = null;
        String dateFormat;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(get_raw_date);
            if (i == 1){
                dateFormat = new SimpleDateFormat("dd MMM , yyyy", Locale.US).format(date);
                return dateFormat;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return get_raw_date;
    }

    private void initializaView(View view) {
        heading = view.findViewById(R.id.view_heading_textview);
        assigned_by = view.findViewById(R.id.view_assignby_textview);
        description = view.findViewById(R.id.view_description_textview);
        millstone = view.findViewById(R.id.view_millstone_textview);
        created_date = view.findViewById(R.id.created_date_textview);

        expand_recycler = view.findViewById(R.id.expandable_milestone_recyclerview);
        expand_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        expand_recycler.setHasFixedSize(true);
    }
}