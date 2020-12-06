package com.android.tusk.Admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tusk.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class create_task_fragment extends Fragment {

    TextInputEditText headingEdx, descriptionEdx, assignbyEdx, assigntoEdx;
    MaterialButton createTaskBtn;

    public create_task_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_task_fragment, container, false);

        initializeView(view);

        return view;
    }

    private void initializeView(View view) {
        headingEdx = view.findViewById(R.id.heading_textInputEditText);
        descriptionEdx = view.findViewById(R.id.description_textInputEditText);
        assignbyEdx = view.findViewById(R.id.assignedby_textInputEditText);
        assigntoEdx = view.findViewById(R.id.assignedto_textInputEditText);

        createTaskBtn = view.findViewById(R.id.create_task_button);
    }
}