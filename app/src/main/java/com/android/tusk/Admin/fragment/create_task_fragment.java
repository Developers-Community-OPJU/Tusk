package com.android.tusk.Admin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.tusk.Admin.model.MilestoneCollectionRequest;
import com.android.tusk.R;
import com.android.tusk.Admin.model.AssignTaskRequest;
import com.android.tusk.Admin.model.AssignTaskResponse;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_task_fragment extends Fragment {

    TextInputEditText headingEdx, descriptionEdx, assignbyEdx, assigntoEdx, duedateEdx;
    TextInputLayout duedate_input_lay;
    MaterialButton createTaskBtn, addMilestonebtn;
    LinearLayout linearlist;

    String formatedDate;
    String formatedTime;

    List<MilestoneCollectionRequest> milestoneCollections = new ArrayList<>();


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

        addMilestonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMilestoneView();
            }
        });

        createTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDataToServer();
            }
        });

        return view;
    }

    private void addMilestoneView() {
        final View view = getLayoutInflater().inflate(R.layout.add_milestone_dynamic_view, null, false);

//        TextInputEditText milestoneTitle = view.findViewById(R.id.milestone_title_edittext);
//        TextInputEditText milestoneDescription = view.findViewById(R.id.milestone_description_edittext);
        ImageView closeView = view.findViewById(R.id.closeImagebutton);

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMilestoneView(view);
            }
        });

        linearlist.addView(view);
    }

    private void removeMilestoneView(View view) {
        linearlist.removeView(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void editTextCalendarIconClickListener() {

        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        calendar.clear();

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
                        String time = String.valueOf(calendar.getTime());
                        formatedTime = dateTimeFormater(time, 1);
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
                formatedDate = dateTimeFormater(materialDatePicker.getHeaderText(), 2);
                Log.d("datec", formatedDate);
            }
        });
    }

    private String dateTimeFormater(String timedate, int i) {
        Date date = null;
        String format;

        try {
            if (i == 1) {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(timedate);
                format = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US).format(date);
                return format;
            }
            if (i == 2){
                date = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(timedate);
                format = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
                return format;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timedate;
    }

    private void pushDataToServer() {
        String heading = headingEdx.getText().toString().trim();
        String description = descriptionEdx.getText().toString().trim();
        String assignby = assignbyEdx.getText().toString().trim();
        String assignto = assigntoEdx.getText().toString().trim();
        String duedate = formatedDate+"T"+formatedTime+"Z";

        getMilestoneFromDynamicView();

        AssignTaskRequest assignTaskRequest = new AssignTaskRequest(heading, description, assignby, assignto, duedate, milestoneCollections);

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

    private void getMilestoneFromDynamicView() {
        //if condition
        checkValidView();
    }

    private boolean checkValidView() {
        milestoneCollections.clear();
        boolean result = true;

        for (int i = 0; i < linearlist.getChildCount(); i++){
            View view = linearlist.getChildAt(i);

            TextInputEditText milestoneTitle = view.findViewById(R.id.milestone_title_edittext);
            TextInputEditText milestoneDescription = view.findViewById(R.id.milestone_description_edittext);

            String title = milestoneTitle.getText().toString();
            String description = milestoneDescription.getText().toString();

            MilestoneCollectionRequest collection = new MilestoneCollectionRequest(title, description);
            milestoneCollections.add(collection);
        }

        return result;
    }

    private void initializeView(View view) {
        headingEdx = view.findViewById(R.id.heading_textInputEditText);
        descriptionEdx = view.findViewById(R.id.description_textInputEditText);
        assignbyEdx = view.findViewById(R.id.assignedby_textInputEditText);
        assigntoEdx = view.findViewById(R.id.assignedto_textInputEditText);
        duedateEdx = view.findViewById(R.id.duedate_textInputEditText);

        duedate_input_lay = view.findViewById(R.id.duedate_textInputLayout);

        createTaskBtn = view.findViewById(R.id.create_task_button);
        addMilestonebtn = view.findViewById(R.id.addMilestoneButton);

        linearlist = view.findViewById(R.id.linearlist);
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}