package com.example.capstone.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Alerts.AssessmentAlertReceiver;
import com.example.capstone.Entities.Assessment;
import com.example.capstone.R;
import com.example.capstone.ViewModels.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_ASSESSMENT_COURSE_ID =
            "com.example.termapp.Activities.ASSESSMENT_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_COURSE_TITLE =
            "com.example.termapp.Activities.ASSESSMENT_COURSE_TITLE";
    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.termapp.Activities.ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_TITLE =
            "com.example.termapp.Activities.ASSESSMENT_TITLE";
    public static final String EXTRA_ASSESSMENT_START_DATE =
            "com.example.termapp.Activities.ASSESSMENT_START_DATE";
    public static final String EXTRA_ASSESSMENT_END_DATE =
            "com.example.termapp.Activities.ASSESSMENT_END_DATE";
    public static final String EXTRA_ASSESSMENT_START_ALARM =
            "com.example.termapp.Activities.ASSESSMENT_START_ALARM";
    public static final String EXTRA_ASSESSMENT_END_ALARM =
            "com.example.termapp.Activities.ASSESSMENT_END_ALARM";
    public static final String EXTRA_ASSESSMENT_TYPE =
            "com.example.termapp.Activities.ASSESSMENT_TYPE";
    public static final int TYPE_PA = 0;
    public static final int TYPE_OA = 1;

    public static int assessmentNumAlert;

    private AssessmentViewModel assessmentViewModel;

    private int courseID;
    private String courseTitle;
    private int assessmentID;
    private String assessmentTitle;
    private String startDate;
    private String endDate;
    private int type;
    private boolean alarmStartEnabled;
    private boolean alarmEndEnabled;
    private TextView textViewTitle;
    private TextView textViewEndDate;
    private TextView textViewStartDate;
    private TextView textViewType;
    private TextView textViewStartAlarm;
    private TextView textViewEndAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        textViewTitle = findViewById(R.id.detailed_assessment_title);
        textViewStartDate = findViewById(R.id.detailed_assessment_start_date);
        textViewEndDate = findViewById(R.id.detailed_assessment_due_date);
        textViewType = findViewById(R.id.detailed_assessment_type);
        textViewStartAlarm = findViewById(R.id.assessment_alarm_start);
        textViewEndAlarm = findViewById(R.id.assessment_alarm_end);

        Intent parentIntent = getIntent();
        courseID = parentIntent.getIntExtra(EXTRA_ASSESSMENT_COURSE_ID, -1);
        assessmentID = parentIntent.getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        courseTitle = parentIntent.getStringExtra(EXTRA_ASSESSMENT_COURSE_TITLE);
        assessmentTitle = parentIntent.getStringExtra(EXTRA_ASSESSMENT_TITLE);
        startDate = parentIntent.getStringExtra(EXTRA_ASSESSMENT_START_DATE);
        endDate = parentIntent.getStringExtra(EXTRA_ASSESSMENT_END_DATE);
        alarmStartEnabled = parentIntent.getBooleanExtra(EXTRA_ASSESSMENT_START_ALARM, false);
        alarmEndEnabled = parentIntent.getBooleanExtra(EXTRA_ASSESSMENT_END_ALARM, false);
        type = parentIntent.getIntExtra(EXTRA_ASSESSMENT_TYPE, -1);
        if (alarmStartEnabled) {
            textViewStartAlarm.setText("Enabled");
        } else {
            textViewStartAlarm.setText("Disabled");
        }
        if (alarmEndEnabled) {
            textViewEndAlarm.setText("Enabled");
        } else {
            textViewEndAlarm.setText("Disabled");
        }

        setTitle(courseTitle + " | " + assessmentTitle);

        textViewTitle.setText(assessmentTitle);
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);
        textViewType.setText(getAssessmentType(type));

        FloatingActionButton buttonEditAssessment = findViewById(R.id.button_edit_assessment);
        buttonEditAssessment.setOnClickListener(v -> {
            Intent editAssessmentIntent = new Intent(AssessmentActivity.this, AddEditAssessmentActivity.class);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, assessmentID);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, courseID);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_TITLE, assessmentTitle);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_DATE, endDate);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_DATE, startDate);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_ALERT, alarmStartEnabled);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_ALERT, alarmEndEnabled);
            editAssessmentIntent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE, type);
            startActivityForResult(editAssessmentIntent, AddEditAssessmentActivity.EDIT_ASSESSMENT_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddEditAssessmentActivity.EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            assessmentID = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);
            assessmentTitle = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_TITLE);
            type = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE, -1);
            endDate = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_DATE);
            startDate = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_DATE);
            alarmStartEnabled = data.getBooleanExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_ALERT, false);
            alarmEndEnabled = data.getBooleanExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_ALERT, false);

            setTitle(courseTitle + " | " + assessmentTitle);
            if (assessmentID == -1
                    || type == -1) {
                Toast.makeText(this, "Assessment Not Saved", Toast.LENGTH_SHORT).show();
                return;
            }

            textViewTitle.setText(assessmentTitle);
            textViewEndDate.setText(endDate);
            textViewStartDate.setText(startDate);
            textViewType.setText(getAssessmentType(type));
            if (alarmStartEnabled) {
                enableStartAlarm(startDate, assessmentTitle);
                textViewStartAlarm.setText("Enabled");
            } else {
                textViewStartAlarm.setText("Disabled");
            }
            if (alarmEndEnabled) {
                enableEndAlarm(endDate, assessmentTitle);
                textViewEndAlarm.setText("Enabled");
            } else {
                textViewEndAlarm.setText("Disabled");
            }

            Assessment assessment = new Assessment(courseID,
                    assessmentTitle, type, startDate, endDate,
                    alarmStartEnabled, alarmEndEnabled);

            assessment.setId(assessmentID);
            assessmentViewModel.update(assessment);

            Toast.makeText(this, "Assessment updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Assessment not updated", Toast.LENGTH_SHORT).show();
        }
    }
    private void enableStartAlarm(String date, String title) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            calendar.setTime(format.parse(date));
            calendar.set(Calendar.HOUR, 8);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(AssessmentActivity.this, AssessmentAlertReceiver.class);
        intent.putExtra("key3", title);
        intent.putExtra("key4", title + " begins " + date);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this,
                ++assessmentNumAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }
    private void enableEndAlarm(String date, String title) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            calendar.setTime(format.parse(date));
            calendar.set(Calendar.HOUR, 8);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(AssessmentActivity.this, AssessmentAlertReceiver.class);
        intent.putExtra("key3", title);
        intent.putExtra("key4", title + " ends " + date);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this,
                ++assessmentNumAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    public static String getAssessmentType(int type) {
        String result;
        switch (type) {
            case TYPE_PA:
                result = "PA";
                break;
            case TYPE_OA:
                result = "OA";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
}