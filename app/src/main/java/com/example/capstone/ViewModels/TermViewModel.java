package com.example.capstone.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstone.Database.Repository;
import com.example.capstone.Entities.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Term>> allTermsLive;
    private LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTermsLive = repository.getAllTerms();
        allTerms = repository.getTerms();
    }

    public void insert(Term term) {
        repository.insert(term);
    }

    public void update(Term term) {
        repository.update(term);
    }

    public void delete(Term term) {
        repository.delete(term);
    }

    public LiveData<List<Term>> getAllTermsLive() {
        return allTermsLive;
    }
    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }
}

