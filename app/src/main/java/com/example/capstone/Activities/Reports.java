package com.example.capstone.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.capstone.Adapters.ReportAdapter;
import com.example.capstone.Entities.Term;
import com.example.capstone.R;
import com.example.capstone.ViewModels.TermViewModel;

import java.util.List;

public class Reports extends AppCompatActivity {
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        RecyclerView recyclerView = findViewById(R.id.reports_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReportAdapter adapter = new ReportAdapter();
                recyclerView.setAdapter(adapter);

                termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication())).get(TermViewModel.class);

                termViewModel.getAllTermsLive().observe(this, new Observer<List<Term>>() {
                    @Override
                    public void onChanged(List<Term> terms) {
                        adapter.setTerms(terms);
                    }
                });
    }
}