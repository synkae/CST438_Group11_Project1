package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codepath.synkae.cst438_proj1.models.Job;

public class JobDetailActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvCompany;
    private TextView tvCategory;
    private TextView tvJobType;
    private TextView tvSalary;
    private TextView tvDescription;
    private static String TAG = "JobDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvCompany = findViewById(R.id.tvCompany);
        tvCategory = findViewById(R.id.tvCategory);
        tvJobType = findViewById(R.id.tvJobType);
        tvSalary = findViewById(R.id.tvSalary);
        tvDescription = findViewById(R.id.tvDescription);
        Intent intent = getIntent();
        Job job = (Job)intent.getSerializableExtra("job");
        loadDetails(job);
    }

    //loads the details of the job which the user selected from
    void loadDetails(Job job){
        tvTitle.setText("Title: " + job.getTitle());
        tvCompany.setText("Company: " + job.getCompanyName());
        tvCategory.setText("Category: " + job.getCategory());
        tvJobType.setText("Job Type: " + job.getJob_type());
        if (job.getSalary().equals("")){
            tvSalary.setText("Salary: Not stated");
        }
        else{
            tvSalary.setText("Salary: " + job.getSalary());
        }
        if (job.getDescription() == null){
            tvDescription.setText("No Description avaialable");
        }
        else{
            tvDescription.setText(HtmlCompat.fromHtml(job.getDescription(), 0));
        }

    }
}