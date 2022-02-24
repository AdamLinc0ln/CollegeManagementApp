package com.example.capstone.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstone.Entities.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM course_table WHERE termID= :termID ORDER BY id ASC")
    LiveData<List<Course>> getLiveTermCourses(int termID);

    @Query("SELECT * FROM course_table WHERE termID= :termID ORDER BY id ASC")
    List<Course> getTermCourses(int termID);
}