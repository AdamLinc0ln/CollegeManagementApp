package com.example.capstone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.capstone.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditCourseActivity extends AppCompatActivity {

    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.termapp.Activities.COURSE_TITLE";
    public static final String EXTRA_COURSE_START_DATE =
            "com.example.termapp.Activities.COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE =
            "com.example.termapp.Activities.COURSE_END_DATE";
    public static final String EXTRA_COURSE_START_ALERT =
            "com.example.termapp.Activities.COURSE_START_ALERT";
    public static final String EXTRA_COURSE_END_ALERT =
            "com.example.termapp.Activities.COURSE_END_ALERT";
    public static final String EXTRA_COURSE_STATUS =
            "com.example.termapp.Activities.COURSE_STATUS";
    public static final String EXTRA_COURSE_MENTOR_NAME =
            "com.example.termapp.Activities.COURSE_MENTOR_NAME";
    public static final String EXTRA_COURSE_MENTOR_PHONE =
            "com.example.termapp.Activities.COURSE_MENTOR_PHONE";
    public static final String EXTRA_COURSE_MENTOR_EMAIL =
            "com.example.termapp.Activities.COURSE_MENTOR_EMAIL";

    public static final int REQUEST_ADD_COURSE = 1;
    public static final int REQUEST_EDIT_COURSE = 2;


    private Calendar calendarStartDate;
    private Calendar calendarEndDate;

    private EditText textTitle;
    private EditText textStartDate;
    private EditText textEndDate;
    private CheckBox CheckboxCourseStartAlarm;
    private CheckBox CheckboxCourseEndAlarm;
    private RadioGroup editRadioStatus;
    private EditText editTextCourseMentorName;
    private EditText editTextCourseMentorPhone;
    private EditText editTextCourseMentorEmail;
    private RadioButton radioButtonInProgress;
    private RadioButton radioButtonCompleted;
    private RadioButton radioButtonDropped;
    private RadioButton radioButtonPlanToTake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        textTitle = findViewById(R.id.course_title);
        textStartDate = findViewById(R.id.course_start_date);
        textEndDate = findViewById(R.id.course_end_date);
        CheckboxCourseStartAlarm = findViewById(R.id.course_start_alarm);
        CheckboxCourseEndAlarm = findViewById(R.id.course_end_alarm);
        editRadioStatus = findViewById(R.id.course_radio_status);
        editTextCourseMentorName = findViewById(R.id.course_instructor_name);
        editTextCourseMentorPhone = findViewById(R.id.course_instructor_phone_number);
        editTextCourseMentorEmail = findViewById(R.id.course_instructor_email_address);
        radioButtonInProgress = findViewById(R.id.radio_course_status_in_progress);
        radioButtonCompleted = findViewById(R.id.radio_course_status_completed);
        radioButtonPlanToTake = findViewById(R.id.radio_course_status_plan_to_take);
        radioButtonDropped = findViewById(R.id.radio_course_status_dropped);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        calendarStartDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calendarStartDate.set(Calendar.YEAR, year);
            calendarStartDate.set(Calendar.MONTH, month);
            calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(textStartDate, calendarStartDate);
        };
        textStartDate.setOnClickListener(v -> new DatePickerDialog(AddEditCourseActivity.this,
                startDate,
                calendarStartDate.get(Calendar.YEAR),
                calendarStartDate.get(Calendar.MONTH),
                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show());

        calendarEndDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener endDate = (view, year, month, dayOfMonth) -> {
            calendarEndDate.set(Calendar.YEAR, year);
            calendarEndDate.set(Calendar.MONTH, month);
            calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(textEndDate, calendarEndDate);
        };
        textEndDate.setOnClickListener(v -> new DatePickerDialog(AddEditCourseActivity.this,
                endDate,
                calendarEndDate.get(Calendar.YEAR),
                calendarEndDate.get(Calendar.MONTH),
                calendarEndDate.get(Calendar.DAY_OF_MONTH)).show());
        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra(EXTRA_COURSE_ID)) {
            setTitle("Edit Course");
            textTitle.setText(parentIntent.getStringExtra(EXTRA_COURSE_TITLE));
            textStartDate.setText(parentIntent.getStringExtra(EXTRA_COURSE_START_DATE));
            textEndDate.setText(parentIntent.getStringExtra(EXTRA_COURSE_END_DATE));
            editTextCourseMentorName.setText(parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_NAME));
            editTextCourseMentorPhone.setText(parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_PHONE));
            editTextCourseMentorEmail.setText(parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_EMAIL));
            editRadioStatus.check(getButtonId(parentIntent.getIntExtra(EXTRA_COURSE_STATUS, -1)));

            if (parentIntent.getBooleanExtra(EXTRA_COURSE_START_ALERT, false)) {
                CheckboxCourseStartAlarm.performClick();
            }
            if (parentIntent.getBooleanExtra(EXTRA_COURSE_END_ALERT, false)) {
                CheckboxCourseEndAlarm.performClick();
            }

        } else {
            setTitle("Add Course");
        }
    }

    private void updateLabel(EditText editTextDate, Calendar calendarDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        editTextDate.setText(sdf.format(calendarDate.getTime()));
    }


    private int getButtonId(int id) {
        int buttonId;
        switch (id) {
            case CourseActivity.STATUS_DROPPED:
                buttonId = R.id.radio_course_status_dropped;
                break;
            case CourseActivity.STATUS_PLAN_TO_TAKE:
                buttonId = R.id.radio_course_status_plan_to_take;
                break;
            case CourseActivity.STATUS_IN_PROGRESS:
                buttonId = R.id.radio_course_status_in_progress;
                break;
            case CourseActivity.STATUS_COMPLETED:
                buttonId = R.id.radio_course_status_completed;
                break;
            default:
                buttonId = -1;
        }
        return buttonId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_edit_save:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveCourse() {
        String courseTitle = textTitle.getText().toString();
        String courseStartDate = textStartDate.getText().toString();
        String courseEndDate = textEndDate.getText().toString();
        int courseStatus = getRadioStatus(editRadioStatus.getCheckedRadioButtonId());
        String courseMentorName = editTextCourseMentorName.getText().toString();
        String courseMentorPhone = editTextCourseMentorPhone.getText().toString();
        String courseMentorEmail = editTextCourseMentorEmail.getText().toString();
        boolean courseStartAlarm = CheckboxCourseStartAlarm.isChecked();
        boolean courseEndAlarm = CheckboxCourseEndAlarm.isChecked();

        if (courseTitle.trim().isEmpty() || courseStartDate.trim().isEmpty()
                || courseEndDate.trim().isEmpty() || courseMentorEmail.trim().isEmpty()
                || courseMentorName.trim().isEmpty() || courseMentorPhone.trim().isEmpty()
                || courseStatus == -1) {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_TITLE, courseTitle);
        data.putExtra(EXTRA_COURSE_START_DATE, courseStartDate);
        data.putExtra(EXTRA_COURSE_END_DATE, courseEndDate);
        data.putExtra(EXTRA_COURSE_MENTOR_EMAIL, courseMentorEmail);
        data.putExtra(EXTRA_COURSE_MENTOR_NAME, courseMentorName);
        data.putExtra(EXTRA_COURSE_MENTOR_PHONE, courseMentorPhone);
        data.putExtra(EXTRA_COURSE_STATUS, courseStatus);
        data.putExtra(EXTRA_COURSE_START_ALERT, courseStartAlarm);
        data.putExtra(EXTRA_COURSE_END_ALERT, courseEndAlarm);

        int courseID = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if (courseID != -1) {
            data.putExtra(EXTRA_COURSE_ID, courseID);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    private int getRadioStatus(int buttonId) {
        int statusId;
        switch (buttonId) {
            case R.id.radio_course_status_dropped:
                statusId = CourseActivity.STATUS_DROPPED;
                break;
            case R.id.radio_course_status_plan_to_take:
                statusId = CourseActivity.STATUS_PLAN_TO_TAKE;
                break;
            case R.id.radio_course_status_in_progress:
                statusId = CourseActivity.STATUS_IN_PROGRESS;
                break;
            case R.id.radio_course_status_completed:
                statusId = CourseActivity.STATUS_COMPLETED;
                break;
            default:
                statusId = -1;
        }
        return statusId;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}