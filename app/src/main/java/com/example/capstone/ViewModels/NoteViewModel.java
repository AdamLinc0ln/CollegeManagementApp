package com.example.capstone.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.capstone.Database.Repository;
import com.example.capstone.Entities.Note;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {
    private Repository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public LiveData<List<Note>> getCourseNotes(int courseId){
        return repository.getCourseNotes(courseId);
    }
}

