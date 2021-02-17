package com.codepath.synkae.cst438_proj1.db;

//import android.arch.persistence.room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.codepath.synkae.cst438_proj1.User;
import com.codepath.synkae.cst438_proj1.models.Job;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Insert
    void insert(Job...Job);

    @Update
    void update(Job...Job);

    @Delete
    void delete(Job...Job);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.SAVEDJOBS_TABLE + " WHERE mUserId = :mUserId")
    List<Job> getAllSavedJobsByUserId(int mUserId);

    @Query("DELETE FROM " + AppDatabase.SAVEDJOBS_TABLE +" WHERE saveJId = :saveJId")
    void deleteSjob(int saveJId);


}
