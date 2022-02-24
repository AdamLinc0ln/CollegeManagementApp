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

import com.example.capstone.Adapters.CourseAdapter;
import com.example.capstone.Entities.Course;
import com.example.capstone.R;
import com.example.capstone.ViewModels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class CourseListActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_TERM_ID =
            "com.example.termapp.Activities.TERM_ID";
    public static final String EXTRA_COURSE_TERM_TITLE =
            "com.example.termapp.Activities.TERM_TITLE";
    private int termID;
    private String termTitle;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCourseIntent = new Intent(CourseListActivity.this,
                        AddEditCourseActivity.class);
                startActivityForResult(addCourseIntent, AddEditCourseActivity.REQUEST_ADD_COURSE);
            }
        });
        Intent courseListIntent = getIntent();
        termID = courseListIntent.getIntExtra(EXTRA_COURSE_TERM_ID, -1);
        termTitle = courseListIntent.getStringExtra(EXTRA_COURSE_TERM_TITLE);
        setTitle(termTitle + " Courses");

        RecyclerView recyclerView = findViewById(R.id.course_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);


        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getTermCoursesLive(termID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course deletedCourse = adapter.getCourseAt(viewHolder.getAdapterPosition());
                courseViewModel.delete(deletedCourse);
                Toast.makeText(CourseListActivity.this, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(CourseListActivity.this, CourseActivity.class);
                intent.putExtra(CourseActivity.EXTRA_COURSE_TERM_ID, termID);
                intent.putExtra(CourseActivity.EXTRA_COURSE_ID, course.getId());
                intent.putExtra(CourseActivity.EXTRA_COURSE_TITLE, course.getTitle());
                intent.putExtra(CourseActivity.EXTRA_COURSE_START_DATE, course.getStart());
                intent.putExtra(CourseActivity.EXTRA_COURSE_END_DATE, course.getEnd());
                intent.putExtra(CourseActivity.EXTRA_COURSE_STATUS, course.getStatus());
                intent.putExtra(CourseActivity.EXTRA_COURSE_MENTOR_NAME, course.getMentorName());
                intent.putExtra(CourseActivity.EXTRA_COURSE_MENTOR_EMAIL, course.getMentorEmail());
                intent.putExtra(CourseActivity.EXTRA_COURSE_MENTOR_PHONE, course.getMentorPhone());
                intent.putExtra(CourseActivity.EXTRA_COURSE_START_ALERT, course.isStartAlert());
                intent.putExtra(CourseActivity.EXTRA_COURSE_END_ALERT, course.isEndAlert());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddEditCourseActivity.REQUEST_ADD_COURSE && resultCode == RESULT_OK) {
            int termID = getIntent().getIntExtra(EXTRA_COURSE_TERM_ID, -1);
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE);
            String startDate = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE);
            String endDate = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE);
            String mentorName = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME);
            String mentorPhone = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE);
            String mentorEmail = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL);
            int status = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, -1);
            boolean startAlert = data.getBooleanExtra(AddEditCourseActivity.EXTRA_COURSE_START_ALERT, false);
            boolean endAlert = data.getBooleanExtra(AddEditCourseActivity.EXTRA_COURSE_END_ALERT, false);

            if (termID == -1) throw new AssertionError("Term ID cannot be -1");

            Course course = new Course(termID, title, startDate, endDate, mentorName, mentorPhone,
                    mentorEmail, status, startAlert, endAlert);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course Not Saved.", Toast.LENGTH_SHORT).show();
        }
    }
}