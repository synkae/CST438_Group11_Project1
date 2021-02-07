package com.codepath.synkae.cst438_proj1.db;

//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codepath.synkae.cst438_proj1.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract DAO getDAO();
}
