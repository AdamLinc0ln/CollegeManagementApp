package com.example.capstone.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.capstone.Entities.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert
    void insert (Term term);
    @Update
    void update (Term term);
    @Delete
    void delete (Term term);
    @Query("DELETE FROM term_table")
    void deleteAllTerms();
    @Query("SELECT * FROM term_table ORDER BY id ASC")
    LiveData<List<Term>> getAllTerms();
    @Query("SELECT * FROM term_table ORDER BY id DESC")
    LiveData<List<Term>> getTerms();
}

