package com.codepath.synkae.cst438_proj1.db;

import androidx.room.*;

import com.codepath.synkae.cst438_proj1.models.SavedJobs;

@Dao
public interface SavedJobDao {

    @Insert
    void insert(SavedJobs...savedJobs);

    @Update
    void update(SavedJobs...savedJobs);

    @Delete
    void delete(SavedJobs...savedJobs);

}
