package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.models.Job;
import com.codepath.synkae.cst438_proj1.db.DAO;

import java.util.ArrayList;
import java.util.List;

public class SavedJobsActivity extends AppCompatActivity {

    private int tUserId;
    private RecyclerView SJLRview;
    public JobRecycleAdapter adapter;
    private DAO DAO;
    private BroadcastReceiver mReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs);

        tUserId = getIntent().getIntExtra("userIdKey", -1);
        SJLRview = findViewById(R.id.SJL);

        getDatabase();
        List<Job> jobsList = DAO.getAllSavedJobsByUserId(tUserId);
        ArrayList<Job> convJobList = new ArrayList<>(jobsList.size());
        convJobList.addAll(jobsList);
        initRecyclerView(convJobList);


    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    private void initRecyclerView(ArrayList<Job> jobList){
        adapter = new JobRecycleAdapter(jobList, tUserId, "view", this);
        SJLRview.setHasFixedSize(true);
        SJLRview.setAdapter(adapter);
        SJLRview.setLayoutManager(new LinearLayoutManager(this));
    }

}