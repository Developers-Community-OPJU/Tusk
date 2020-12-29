package com.android.tusk.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.tusk.Admin.model.AssignTaskRequest;
import com.android.tusk.Admin.model.AssignTaskResponse;
import com.android.tusk.Admin.model.MilestoneCollectionRequest;
import com.android.tusk.R;
import com.android.tusk.retrofit.APIclient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_task extends AppCompatActivity {

    public static final String TASK_HEADING = "heading";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_ASSIGN_BY = "assign_by";
    public static final String TASK_DUE_DATE = "due_date";
    public static final String TASK_MILESTONES = "milestones";

    TextInputEditText headingEdx, descriptionEdx, assignbyEdx, duedateEdx;
    TextInputLayout duedate_input_lay;
    MaterialButton nextBtn, addMilestonebtn;
    LinearLayout linearlist;

    Toolbar toolbar;

    String formatedDate;
    String formatedTime;

    ArrayList<MilestoneCollectionRequest> milestoneCollections = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_create_task_activity);

        initializeView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setCustomView(R.layout.admin_actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        editTextCalendarIconClickListener();

        addMilestonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMilestoneView();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToActivity();
            }
        });

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
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
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

    private void sendDataToActivity() {
        String heading = headingEdx.getText().toString().trim();
        String description = descriptionEdx.getText().toString().trim();
        String assignby = assignbyEdx.getText().toString().trim();
        String duedate = formatedDate+"T"+formatedTime+"Z";

        getMilestoneFromDynamicView();

        Intent intent = new Intent(create_task.this, assigning_task.class);
        intent.putExtra(TASK_HEADING, heading);
        intent.putExtra(TASK_DESCRIPTION, description);
        intent.putExtra(TASK_ASSIGN_BY, assignby);
        intent.putExtra(TASK_DUE_DATE, duedate);
        intent.putParcelableArrayListExtra(TASK_MILESTONES, milestoneCollections);

        startActivity(intent);
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

    private void initializeView() {
        headingEdx = findViewById(R.id.heading_textInputEditText);
        descriptionEdx = findViewById(R.id.description_textInputEditText);
        assignbyEdx = findViewById(R.id.assignedby_textInputEditText);
        duedateEdx = findViewById(R.id.duedate_textInputEditText);

        duedate_input_lay = findViewById(R.id.duedate_textInputLayout);

        nextBtn = findViewById(R.id.next_button);
        addMilestonebtn = findViewById(R.id.addMilestoneButton);

        linearlist = findViewById(R.id.linearlist);

        toolbar = findViewById(R.id.admin_dashboard_toolbar);
    }

    private void ToastMassage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
