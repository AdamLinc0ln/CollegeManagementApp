package com.example.capstone.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.Alerts.CourseAlertReceiver;
import com.example.capstone.Entities.Course;
import com.example.capstone.R;
import com.example.capstone.ViewModels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_STATUS =
            "com.example.termapp.Activities.COURSE_STATUS";
    public static final String EXTRA_COURSE_TERM_ID =
            "com.example.termapp.Activities.COURSE_TERM_ID";
    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.termapp.Activities.COURSE_TITLE";
    public static final String EXTRA_COURSE_START_DATE =
            "com.example.termapp.Activities.COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE =
            "com.example.termapp.Activities.COURSE_END_DATE";
    public static final String EXTRA_COURSE_MENTOR_NAME =
            "com.example.termapp.Activities.COURSE_MENTOR_NAME";
    public static final String EXTRA_COURSE_MENTOR_PHONE =
            "com.example.termapp.Activities.COURSE_MENTOR_PHONE";
    public static final String EXTRA_COURSE_MENTOR_EMAIL =
            "com.example.termapp.Activities.COURSE_MENTOR_EMAIL";
    public static final String EXTRA_COURSE_START_ALERT =
            "com.example.termapp.Activities.COURSE_START_ALERT";
    public static final String EXTRA_COURSE_END_ALERT =
            "com.example.termapp.Activities.COURSE_END_ALERT";
    public static int courseNumAlert;

    public static final int STATUS_IN_PROGRESS = 0;
    public static final int STATUS_COMPLETED = 1;
    public static final int STATUS_DROPPED = 2;
    public static final int STATUS_PLAN_TO_TAKE = 3;
    public static final int EDIT_COURSE_REQUEST = 5;

    private CourseViewModel courseViewModel;

    private int termID;
    private int courseID;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewCourseStatus;
    private TextView textViewCourseMentorName;
    private TextView textViewCourseMentorPhone;
    private TextView textViewCourseMentorEmail;
    private TextView textViewStartAlarm;
    private TextView textViewEndAlarm;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseMentorName;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private int status;
    private boolean startAlarm;
    private boolean endAlarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_course);

        textViewTitle = findViewById(R.id.detailed_course_title);
        textViewStartDate = findViewById(R.id.detailed_course_start_date);
        textViewEndDate = findViewById(R.id.detailed_course_end_date);
        textViewCourseStatus = findViewById(R.id.detailed_course_status);
        textViewCourseMentorName = findViewById(R.id.detailed_course_mentor_name);
        textViewCourseMentorPhone = findViewById(R.id.detailed_course_mentor_phone_number);
        textViewCourseMentorEmail = findViewById(R.id.detailed_course_mentor_email_address);
        textViewStartAlarm = findViewById(R.id.course_start_alarm_text_view);
        textViewEndAlarm = findViewById(R.id.course_end_alarm_text_view);


        Intent parentIntent = getIntent();
        termID = parentIntent.getIntExtra(EXTRA_COURSE_TERM_ID, -1);
        courseID = parentIntent.getIntExtra(EXTRA_COURSE_ID, -1);

        courseTitle = parentIntent.getStringExtra(EXTRA_COURSE_TITLE);
        setTitle(parentIntent.getStringExtra(EXTRA_COURSE_TITLE));
        textViewTitle.setText(courseTitle);

        courseStartDate = parentIntent.getStringExtra(EXTRA_COURSE_START_DATE);
        courseEndDate = parentIntent.getStringExtra(EXTRA_COURSE_END_DATE);
        courseMentorName = parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_NAME);
        courseMentorPhone = parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_PHONE);
        courseMentorEmail = parentIntent.getStringExtra(EXTRA_COURSE_MENTOR_EMAIL);
        status = parentIntent.getIntExtra(EXTRA_COURSE_STATUS, -1);
        startAlarm = parentIntent.getBooleanExtra(EXTRA_COURSE_START_ALERT, false);
        endAlarm = parentIntent.getBooleanExtra(EXTRA_COURSE_END_ALERT, false);

        textViewStartDate.setText(courseStartDate);
        textViewEndDate.setText(courseEndDate);
        textViewCourseStatus.setText(CourseActivity.getStatus(status));
        textViewCourseMentorName.setText(courseMentorName);
        textViewCourseMentorPhone.setText(courseMentorPhone);
        textViewCourseMentorEmail.setText(courseMentorEmail);
        if (startAlarm) {
            textViewStartAlarm.setText("Enabled");
        } else {
            textViewStartAlarm.setText("Disabled");
        }
        if (endAlarm) {
            textViewEndAlarm.setText("Enabled");
        } else {
            textViewEndAlarm.setText("Disabled");
        }

        FloatingActionButton buttonEditCourse = findViewById(R.id.button_edit_course);
        buttonEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editCourseIntent = new Intent(CourseActivity.this, AddEditCourseActivity.class);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, courseID);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE, courseTitle);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE, courseStartDate);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE, courseEndDate);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME, courseMentorName);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE, courseMentorPhone);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL, courseMentorEmail);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_START_ALERT, startAlarm);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_END_ALERT, endAlarm);
                editCourseIntent.putExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, status);
                startActivityForResult(editCourseIntent, EDIT_COURSE_REQUEST);
            }
        });
    }

    public static String getStatus(int status) {
        String result;
        switch (status) {
            case STATUS_IN_PROGRESS:
                result = "In Progress";
                break;
            case STATUS_COMPLETED:
                result = "Completed";
                break;
            case STATUS_DROPPED:
                result = "Dropped";
                break;
            case STATUS_PLAN_TO_TAKE:
                result = "Plan to take";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            setTitle(data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE));
            int courseID = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
            courseTitle = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE);
            courseStartDate = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE);
            courseEndDate = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE);
            courseMentorName = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME);
            courseMentorPhone = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE);
            courseMentorEmail = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL);
            status = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, -1);
            startAlarm = data.getBooleanExtra(AddEditCourseActivity.EXTRA_COURSE_START_ALERT, false);
            endAlarm = data.getBooleanExtra(AddEditCourseActivity.EXTRA_COURSE_END_ALERT, false);

            if (courseID == -1) {
                Toast.makeText(this, "Course Not Saved.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (startAlarm) {
                enableStartAlarm(courseStartDate, courseTitle);
                textViewStartAlarm.setText("Enabled");
            } else {
                textViewStartAlarm.setText("Disabled");
            }
            if (endAlarm) {
                enableEndAlarm(courseEndDate, courseTitle);
                textViewEndAlarm.setText("Enabled");
            } else {
                textViewEndAlarm.setText("Disabled");
            }
            textViewTitle.setText(courseTitle);
            textViewStartDate.setText(courseStartDate);
            textViewEndDate.setText(courseEndDate);
            textViewCourseStatus.setText(getStatus(status));
            textViewCourseMentorName.setText(courseMentorName);
            textViewCourseMentorPhone.setText(courseMentorPhone);
            textViewCourseMentorEmail.setText(courseMentorEmail);

            Course course = new Course(termID, courseTitle, courseStartDate, courseEndDate,
                    courseMentorName, courseMentorPhone, courseMentorEmail, status, startAlarm,
                    endAlarm);
            course.setId(courseID);
            courseViewModel.update(course);
            Toast.makeText(this, "Course Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course Not Updated", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(CourseActivity.this, CourseAlertReceiver.class);
        intent.putExtra("key", title);
        intent.putExtra("key2", title + " begins " + date);
        PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this,
                ++courseNumAlert, intent, 0);
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
        Intent intent = new Intent(CourseActivity.this, CourseAlertReceiver.class);
        intent.putExtra("key", title);
        intent.putExtra("key2", title + " ends " + date);
        PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this,
                ++courseNumAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detailed_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.to_notes_list:
                Intent notesIntent = new Intent(CourseActivity.this,
                        NotesListActivity.class);
                notesIntent.putExtra(NotesListActivity.EXTRA_COURSE_ID, courseID);
                notesIntent.putExtra(NotesListActivity.EXTRA_COURSE_TITLE, courseTitle);
                startActivity(notesIntent);
                return true;
            case R.id.to_assessments_list:
                Intent assessments = new Intent(CourseActivity.this,
                        AssessmentsListActivity.class);
                assessments.putExtra(AssessmentsListActivity.EXTRA_COURSE_ID, courseID);
                assessments.putExtra(AssessmentsListActivity.EXTRA_COURSE_TITLE, courseTitle);
                startActivity(assessments);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
