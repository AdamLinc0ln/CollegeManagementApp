package com.example.capstone.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.Adapters.AssessmentAdapter;
import com.example.capstone.Entities.Assessment;
import com.example.capstone.R;
import com.example.capstone.ViewModels.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentsListActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID =
            "com.example.termapp.Activities.COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.termapp.Activities.COURSE_TITLE";
    public static final int ADD_ASSESSMENT_REQUEST = 1;

    private int courseID;
    private String courseTitle;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_list);

        FloatingActionButton addAssessmentButton = findViewById(R.id.button_add_assessment);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAssessment = new Intent(AssessmentsListActivity.this, AddEditAssessmentActivity.class);
                startActivityForResult(addAssessment, ADD_ASSESSMENT_REQUEST);
            }
        });
        Intent assessmentsList = getIntent();
        courseID = assessmentsList.getIntExtra(EXTRA_COURSE_ID, -1);
        courseTitle = assessmentsList.getStringExtra(EXTRA_COURSE_TITLE);
        setTitle(courseTitle + " Assessments");

        RecyclerView recyclerView = findViewById(R.id.assessment_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getCourseAssessments(courseID).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Assessment deletedAssessment = adapter.getAssessmentAt(viewHolder.getAdapterPosition());
                assessmentViewModel.delete(deletedAssessment);
                Toast.makeText(AssessmentsListActivity.this, "Assessment Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                Intent assessmentIntent = new Intent(AssessmentsListActivity.this, AssessmentActivity.class);
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_COURSE_ID, courseID);
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_COURSE_TITLE, courseTitle);
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_ID, assessment.getId());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_TITLE, assessment.getName());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_TYPE, assessment.getType());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_START_DATE, assessment.getStartDate());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_END_DATE, assessment.getEndDate());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_START_ALARM, assessment.isStartAlert());
                assessmentIntent.putExtra(AssessmentActivity.EXTRA_ASSESSMENT_END_ALARM, assessment.isEndAlert());
                startActivity(assessmentIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int courseID = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
            String assessmentName = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_TITLE);
            int assessmentType = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE, -1);
            String assessmentEndDate = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_DATE);
            String assessmentStartDate = data.getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_DATE);
            boolean assessmentStartAlertEnabled = data.getBooleanExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_START_ALERT, false);
            boolean assessmentEndAlertEnabled = data.getBooleanExtra(AddEditAssessmentActivity.EXTRA_COURSE_ASSESSMENT_END_ALERT, false);

            Assessment assessment = new Assessment(courseID, assessmentName, assessmentType,
                    assessmentStartDate, assessmentEndDate, assessmentStartAlertEnabled, assessmentEndAlertEnabled);
            assessmentViewModel.insert(assessment);
        }
    }
}