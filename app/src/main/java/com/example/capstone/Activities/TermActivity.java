package com.example.capstone.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.Entities.Term;
import com.example.capstone.R;
import com.example.capstone.ViewModels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.termapp.Activities.ID";
    public static final String EXTRA_TERM_TITLE =
            "com.example.termapp.Activities.TERM_TITLE";
    public static final String EXTRA_START_DATE =
            "com.example.termapp.Activities.START_DATE";
    public static final String EXTRA_END_DATE =
            "com.example.termapp.Activities.END_DATE";
    public static final String EXTRA_TIMESTAMP =
            "com.example.termapp.Activities.EXTRA_TIMESTAMP";

    public static final int EDIT_TERM_REQUEST = 2;
    private TermViewModel termViewModel;
    private int termID;
    private TextView textViewTitle;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewTimestamp;
    private Button courseListButton;
    private String startDate;
    private String endDate;
    private String termTitle;
    private String timestamp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        setContentView(R.layout.activity_term);

        textViewTitle = findViewById(R.id.text_view_detailed_term_title);
        textViewStartDate = findViewById(R.id.text_view_detailed_term_start_date);
        textViewEndDate = findViewById(R.id.text_view_detailed_term_end_date);
        textViewTimestamp = findViewById(R.id.start_timestamp);
        courseListButton = findViewById(R.id.course_list_button);

        Intent parentIntent = getIntent();
        termID = parentIntent.getIntExtra(EXTRA_ID, -1);
        termTitle = parentIntent.getStringExtra(EXTRA_TERM_TITLE);
        startDate = parentIntent.getStringExtra(EXTRA_START_DATE);
        endDate = parentIntent.getStringExtra(EXTRA_END_DATE);
        timestamp = parentIntent.getStringExtra(EXTRA_TIMESTAMP);
        textViewTitle.setText(termTitle);
        textViewStartDate.setText(startDate);
        textViewEndDate.setText(endDate);
        textViewTimestamp.setText(timestamp);


        setTitle(parentIntent.getStringExtra(EXTRA_TERM_TITLE));

        courseListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseListIntent = new Intent(TermActivity.this, CourseListActivity.class);
                courseListIntent.putExtra(CourseListActivity.EXTRA_COURSE_TERM_ID, termID);
                courseListIntent.putExtra(CourseListActivity.EXTRA_COURSE_TERM_TITLE, termTitle);
                startActivity(courseListIntent);
            }
        });

        FloatingActionButton buttonEditTerm = findViewById(R.id.button_edit_term);
        buttonEditTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editTermIntent = new Intent(TermActivity.this, AddEditTermActivity.class);
                editTermIntent.putExtra(AddEditTermActivity.EXTRA_TERM_ID, termID);
                editTermIntent.putExtra(AddEditTermActivity.EXTRA_TERM_TITLE, termTitle);
                editTermIntent.putExtra(AddEditTermActivity.EXTRA_TERM_START_DATE, startDate);
                editTermIntent.putExtra(AddEditTermActivity.EXTRA_TERM_END_DATE, endDate);
                editTermIntent.putExtra(AddEditTermActivity.EXTRA_TIMESTAMP, timestamp);
                startActivityForResult(editTermIntent, EDIT_TERM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            setTitle(data.getStringExtra(AddEditTermActivity.EXTRA_TERM_TITLE));
            termTitle = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_TITLE);
            startDate = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_START_DATE);
            endDate = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_END_DATE);
            timestamp = data.getStringExtra(AddEditTermActivity.EXTRA_TIMESTAMP);
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_TERM_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
                return;
            }
            textViewTitle.setText(termTitle);
            textViewStartDate.setText(startDate);
            textViewEndDate.setText(endDate);
            textViewTimestamp.setText(timestamp);

            Term term = new Term(termTitle, startDate, endDate, timestamp);
            term.setId(id);
            termViewModel.update(term);
            Toast.makeText(this, "Term Updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved.", Toast.LENGTH_SHORT).show();
        }
    }
}