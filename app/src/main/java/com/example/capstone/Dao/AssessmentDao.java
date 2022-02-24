package com.example.capstone.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstone.Entities.Assessment;

import java.util.List;


@Dao
public interface AssessmentDao {
    @Insert
    void insert(Assessment assessmentEntity);

    @Update
    void update(Assessment assessmentEntity);

    @Delete
    void delete(Assessment assessmentEntity);

    @Query("SELECT * FROM assessment_table WHERE courseID= :courseID ORDER BY id ASC")
    LiveData<List<Assessment>> getCourseAssessments(int courseID);
}
