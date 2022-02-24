package com.example.capstone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.capstone.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditTermActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_ID =
            "com.example.termapp.Activities.TERM_ID";
    public static final String EXTRA_TERM_TITLE =
            "com.example.termapp.Activities.TERM_TITLE";
    public static final String EXTRA_TERM_START_DATE =
            "com.example.termapp.Activities.TERM_START_DATE";
    public static final String EXTRA_TERM_END_DATE =
            "com.example.termapp.Activities.TERM_END_DATE";
    public static final String EXTRA_TIMESTAMP =
            "com.example.termapp.Activities.EXTRA_TIMESTAMP";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private Button timeButton;
    private int hour, minute;
    private Calendar calendarStartDate;
    private Calendar calendarEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);
        timeButton = findViewById(R.id.time_picker_button);
        editTextTitle = findViewById(R.id.term_title);
        editTextStartDate = findViewById(R.id.term_start_date);
        editTextEndDate = findViewById(R.id.term_end_date);

        this.calendarStartDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetStart = (view, year, month, dayOfMonth) -> {
            calendarStartDate.set(Calendar.YEAR, year);
            calendarStartDate.set(Calendar.MONTH, month);
            calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextStartDate, calendarStartDate);
        };
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditTermActivity.this, dateSetStart,
                        calendarStartDate.get(Calendar.YEAR),
                        calendarStartDate.get(Calendar.MONTH),
                        calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        this.calendarEndDate = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetEnd = (view, year, month, dayOfMonth) -> {
            calendarEndDate.set(Calendar.YEAR, year);
            calendarEndDate.set(Calendar.MONTH, month);
            calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(editTextEndDate, calendarEndDate);
        };
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditTermActivity.this,
                        dateSetEnd,
                        calendarEndDate.get(Calendar.YEAR),
                        calendarEndDate.get(Calendar.MONTH),
                        calendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TERM_ID)) {
            setTitle("Edit Term");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_TERM_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_TERM_END_DATE));
            timeButton.setText(intent.getStringExtra(EXTRA_TIMESTAMP));
        } else {
            setTitle("Add Term");
        }
    }

    private void saveTerm() {
        String termTitle = editTextTitle.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();
        String timestamp = timeButton.getText().toString();

        if (termTitle.trim().isEmpty()
                || startDate.trim().isEmpty()
                || endDate.trim().isEmpty() || timestamp.isEmpty()) {
            Toast.makeText(this, "One or more empty fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TIMESTAMP, timestamp);
        data.putExtra(EXTRA_TERM_TITLE, termTitle);
        data.putExtra(EXTRA_TERM_START_DATE, startDate);
        data.putExtra(EXTRA_TERM_END_DATE, endDate);

        int id = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_TERM_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
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

    private void updateLabel(EditText editText, Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}