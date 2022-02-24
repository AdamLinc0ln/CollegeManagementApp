package com.example.capstone.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstone.Database.Repository;
import com.example.capstone.Entities.Assessment;

import java.util.List;


public class AssessmentViewModel extends AndroidViewModel {
    private Repository repository;


    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insert(Assessment assessment){
        repository.insert(assessment);
    }
    public void update(Assessment assessment){
        repository.update(assessment);
    }
    public void delete(Assessment assessment){
        repository.delete(assessment);
    }
    public LiveData<List<Assessment>> getCourseAssessments(int courseID){
        return repository.getCourseAssessments(courseID);
    }
}
