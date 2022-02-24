package com.example.capstone.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.capstone.Adapters.TermAdapter;
import com.example.capstone.Entities.Term;
import com.example.capstone.R;
import com.example.capstone.ViewModels.TermViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private TermAdapter mTermAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mTermAdapter = new TermAdapter();
        buildRecyclerView();

        termViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(TermViewModel.class);

        termViewModel.getAllTermsLive().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                mTermAdapter.setTerms(terms);
            }
        });
        EditText searchBar = findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<Term> filteredList = new ArrayList<>();

        for(Term item: termViewModel.getAllTermsLive().getValue()){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mTermAdapter.filteredList(filteredList);
    }

    private void buildRecyclerView(){
        searchRecyclerView = findViewById(R.id.search_recyclerView);
        searchRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        searchRecyclerView.setLayoutManager(mLayoutManager);
        searchRecyclerView.setAdapter(mTermAdapter);
    }
}