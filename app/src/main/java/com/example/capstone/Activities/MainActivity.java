package com.example.capstone.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.capstone.Adapters.TermAdapter;
import com.example.capstone.Entities.Term;
import com.example.capstone.R;
import com.example.capstone.ViewModels.CourseViewModel;
import com.example.capstone.ViewModels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(TermViewModel.class);

        termViewModel.getAllTermsLive().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });

        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Term deletedTerm = adapter.getTermAt(viewHolder.getAdapterPosition());
                int courses = 0;
                try {
                    courses = courseViewModel.getTermCourses(deletedTerm.getId()).size();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                if (courses > 0) {
                    Toast.makeText(MainActivity.this,
                            "All associated courses must be deleted. Term not deleted",
                            Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();

                } else {
                    termViewModel.delete(deletedTerm);
                    Toast.makeText(MainActivity.this, "Term deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(MainActivity.this, TermActivity.class);
                intent.putExtra(TermActivity.EXTRA_ID, term.getId());
                intent.putExtra(TermActivity.EXTRA_TERM_TITLE, term.getTitle());
                intent.putExtra(TermActivity.EXTRA_START_DATE, term.getStart());
                intent.putExtra(TermActivity.EXTRA_END_DATE, term.getEnd());
                intent.putExtra(TermActivity.EXTRA_TIMESTAMP, term.getTimestamp());
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_TITLE);
            String startDate = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_START_DATE);
            String endDate = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_END_DATE);
            String timestamp = data.getStringExtra(AddEditTermActivity.EXTRA_TIMESTAMP);

            Term term = new Term(title, startDate, endDate, timestamp);
            termViewModel.insert(term);

            Toast.makeText(this, "Term saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }
    }
}