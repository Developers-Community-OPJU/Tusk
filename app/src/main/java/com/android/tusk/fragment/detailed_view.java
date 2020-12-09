package com.android.tusk.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tusk.R;

import static com.android.tusk.fragment.Home.ASSIGNED_BY;
import static com.android.tusk.fragment.Home.DESCRIPTION;
import static com.android.tusk.fragment.Home.HEADING;
import static com.android.tusk.fragment.Home.MILESTONES;

public class detailed_view extends Fragment {

    TextView heading, assigned_by, description, millstone;

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
            int get_millstone = bundle.getInt(MILESTONES);

            heading.setText(get_heading);
            assigned_by.setText(get_assignby);
            description.setText(get_description);
            millstone.setText(get_millstone+" Milestones");
        }
    }

    private void initializaView(View view) {
        heading = view.findViewById(R.id.view_heading_textview);
        assigned_by = view.findViewById(R.id.view_assignby_textview);
        description = view.findViewById(R.id.view_description_textview);
        millstone = view.findViewById(R.id.view_millstone_textview);
    }
}