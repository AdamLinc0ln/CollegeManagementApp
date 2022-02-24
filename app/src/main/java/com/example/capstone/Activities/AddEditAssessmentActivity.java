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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.capstone.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditAssessmentActivity extends AppCompatActivity {

    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.termapp.Activities.ASSESSMENT_ID";
    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";
    public static final String EXTRA_COURSE_ASSESSMENT_TITLE =
            "com.example.termapp.Activities.COURSE_ASSESSMENT_TITLE";
    public static final String EXTRA_COURSE_ASSESSMENT_END_DATE =
            "com.example.termapp.Activities.COURSE_ASSESSMENT_END_DATE";
    public static final String EXTRA_COURSE_ASSESSMENT_START_DATE =
            "com.example.termapp.Activities.COURSE_ASSESSMENT_START_DATE";
    public static final String EXTRA_COURSE_ASSESSMENT_START_ALERT =
            "com.example.termapp.Activities.COURSE_ASSESSMENT_START_ALERT";
    public static final String EXTRA_COURSE_ASSESSMENT_END_ALERT =
            "com.example.termapp.Activities.COURSE_ASSESSMENT_END_ALERT";
    public static final String EXTRA_ASSESSMENT_TYPE =
            "com.example.termapp.Activities.ASSESSMENT_TYPE";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    private Calendar calenderStartDate;
    private Calendar calenderEndDate;
    private int assessmentId;
    private EditText editTextTitle;
    private EditText editTextEndDate;
    private RadioGroup RadioGroupType;
    private CheckBox checkBoxStartAlarm;
    private CheckBox checkBoxEndAlarm;
    private EditText editTextStartDate;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String assessmentTitle;
    private boolean alarmStartEnabled;
    private boolean alarmEndEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        editTextTitle = findViewById(R.id.assessment_title);
        editTextStartDate = findViewById(R.id.assessment_start_date);
        editTextEndDate = findViewById(R.id.assessment_end_date);
        RadioGroupType = findViewById(R.id.assessment_radio_group);
        checkBoxStartAlarm = findViewById(R.id.assessment_start_alarm);
        checkBoxEndAlarm = findViewById(R.id.assessment_end_alarm);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        calenderStartDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDate = (view, year, month, dayOfMonth) -> {
            calenderStartDate.set(Calendar.YEAR, year);
            calenderStartDate.set(Calendar.MONTH, month);
            calenderStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextStartDate,calenderStartDate);
        };
        editTextStartDate.setOnClickListener(v -> new DatePickerDialog(AddEditAssessmentActivity.this,
                startDate,
                calenderStartDate.get(Calendar.YEAR),
                calenderStartDate.get(Calendar.MONTH),
                calenderStartDate.get(Calendar.DAY_OF_MONTH)).show());

        calenderEndDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener endDate = (view, year, month, dayOfMonth) -> {
            calenderEndDate.set(Calendar.YEAR, year);
            calenderEndDate.set(Calendar.MONTH, month);
            calenderEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextEndDate,calenderEndDate);
        };
        editTextEndDate.setOnClickListener(v -> new DatePickerDialog(AddEditAssessmentActivity.this,
                endDate,
                calenderEndDate.get(Calendar.YEAR),
                calenderEndDate.get(Calendar.MONTH),
                calenderEndDate.get(Calendar.DAY_OF_MONTH)).show());

        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Edit Assessment");
            editTextTitle.setText(parentIntent.getStringExtra(EXTRA_COURSE_ASSESSMENT_TITLE));
            editTextEndDate.setText(parentIntent.getStringExtra(EXTRA_COURSE_ASSESSMENT_END_DATE));
            editTextStartDate.setText(parentIntent.getStringExtra(EXTRA_COURSE_ASSESSMENT_START_DATE));
            RadioGroupType.check(getTypeButtonId(parentIntent.getIntExtra(EXTRA_ASSESSMENT_TYPE, -1)));
            if (parentIntent.getBooleanExtra(EXTRA_COURSE_ASSESSMENT_START_ALERT, false)) {
                checkBoxStartAlarm.performClick();
            }
            if(parentIntent.getBooleanExtra(EXTRA_COURSE_ASSESSMENT_END_ALERT, false)){
                checkBoxEndAlarm.performClick();
            }
        } else {
            setTitle("Add Assessment");
        }
    }

    private void saveAssessment() {
        assessmentTitle = editTextTitle.getText().toString();
        assessmentEndDate = editTextEndDate.getText().toString();
        assessmentStartDate = editTextStartDate.getText().toString();
        alarmStartEnabled = checkBoxStartAlarm.isChecked();
        alarmEndEnabled = checkBoxEndAlarm.isChecked();


        if (assessmentTitle.trim().isEmpty()
                || assessmentEndDate.trim().isEmpty()
                || assessmentStartDate.trim().isEmpty()
                || getTypeButtonId(RadioGroupType.getCheckedRadioButtonId()) != -1) {
            Toast.makeText(this, "Empty Fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_ASSESSMENT_TITLE, assessmentTitle);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, getRadioType(RadioGroupType.getCheckedRadioButtonId()));
        data.putExtra(EXTRA_COURSE_ASSESSMENT_START_DATE, assessmentStartDate);
        data.putExtra(EXTRA_COURSE_ASSESSMENT_END_DATE, assessmentEndDate);
        data.putExtra(EXTRA_COURSE_ASSESSMENT_START_ALERT, alarmStartEnabled);
        data.putExtra(EXTRA_COURSE_ASSESSMENT_END_ALERT, alarmEndEnabled);
        assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if (assessmentId != -1)
            data.putExtra(EXTRA_ASSESSMENT_ID, assessmentId);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_edit_save:
                saveAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private int getTypeButtonId(int id) {
        int button_id;
        switch (id) {
            case AssessmentActivity.TYPE_PA:
                button_id = R.id.performance_assessment_radio_button;
                break;
            case AssessmentActivity.TYPE_OA:
                button_id = R.id.objective_assessment_radio_button;
                break;
            default:
                button_id = -1;
        }
        return button_id;
    }

    private int getRadioType(int buttonId) {
        int typeId;
        switch (buttonId) {
            case R.id.performance_assessment_radio_button:
                typeId = AssessmentActivity.TYPE_PA;
                break;
            case R.id.objective_assessment_radio_button:
                typeId = AssessmentActivity.TYPE_OA;
                break;
            default:
                typeId = -1;
        }
        return typeId;
    }

    private void updateLabel(EditText editTextDate, Calendar calendarDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        editTextDate.setText(sdf.format(calendarDate.getTime()));
    }
}