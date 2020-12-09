package com.android.tusk.Admin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.tusk.R;
import com.android.tusk.model.AssignTaskRequest;
import com.android.tusk.model.AssignTaskResponse;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_task_fragment extends Fragment {

    TextInputEditText headingEdx, descriptionEdx, assignbyEdx, assigntoEdx, duedateEdx;
    TextInputLayout duedate_input_lay;
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

        editTextCalendarIconClickListener();

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDataToServer();
            }
        });

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void editTextCalendarIconClickListener() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        final long today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Due Date");
        builder.setSelection(today);
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        duedateEdx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (duedateEdx.getRight() - duedateEdx.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        materialDatePicker.show(getFragmentManager(), "MATERIAL_DATE_PICKER");

                        return true;
                    }
                }
                return false;
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                duedateEdx.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    private void pushDataToServer() {
        String heading = headingEdx.getText().toString().trim();
        String description = descriptionEdx.getText().toString().trim();
        String assignby = assignbyEdx.getText().toString().trim();
        String assignto = assigntoEdx.getText().toString().trim();

        AssignTaskRequest assignTaskRequest = new AssignTaskRequest(heading, description, assignby, assignto);

        Call<AssignTaskResponse> taskResponseCall = APIclient.getInterface().getCreateTaskResponse(assignTaskRequest);
        taskResponseCall.enqueue(new Callback<AssignTaskResponse>() {
            @Override
            public void onResponse(Call<AssignTaskResponse> call, Response<AssignTaskResponse> response) {
                if (response.isSuccessful()) {
                    AssignTaskResponse taskResponse = response.body();
                    ToastMassage(taskResponse.getMsg() + "");
                }
            }

            @Override
            public void onFailure(Call<AssignTaskResponse> call, Throwable t) {
                ToastMassage("failed");
            }
        });
    }

    private void initializeView(View view) {
        headingEdx = view.findViewById(R.id.heading_textInputEditText);
        descriptionEdx = view.findViewById(R.id.description_textInputEditText);
        assignbyEdx = view.findViewById(R.id.assignedby_textInputEditText);
        assigntoEdx = view.findViewById(R.id.assignedto_textInputEditText);
        duedateEdx = view.findViewById(R.id.duedate_textInputEditText);

        duedate_input_lay = view.findViewById(R.id.duedate_textInputLayout);

        createTaskBtn = view.findViewById(R.id.create_task_button);
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}