package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.models.Job;
import com.codepath.synkae.cst438_proj1.db.DAO;

import java.util.ArrayList;

public class SavedJobsActivity extends AppCompatActivity {

    private int tUserId;
    private RecyclerView SJLRview;
    private JobRecycleAdapter adapter;
    private DAO DAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

        tUserId = getIntent().getIntExtra("userIdKey", -1);
        SJLRview = findViewById(R.id.SJL);


    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }
}