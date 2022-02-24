package com.example.capstone.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstone.Database.Repository;
import com.example.capstone.Entities.Course;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseViewModel extends AndroidViewModel {
    private Repository repository;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }
    public void insert (Course course){
        repository.insert(course);
    }
    public void update (Course course){
        repository.update(course);
    }
    public void delete (Course course){
        repository.delete(course);
    }
    public LiveData<List<Course>> getTermCoursesLive(int termID){
        return repository.getTermCoursesLive(termID);
    }
    public List<Course> getTermCourses(int termID) throws ExecutionException, InterruptedException{
        return repository.getTermCourses(termID);
    }
}
