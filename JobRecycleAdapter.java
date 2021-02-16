package com.codepath.synkae.cst438_proj1;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.codepath.synkae.cst438_proj1.db.SavedJobDao;
import com.codepath.synkae.cst438_proj1.models.Job;
import com.codepath.synkae.cst438_proj1.models.SavedJobs;

import java.util.ArrayList;

public class JobRecycleAdapter extends RecyclerView.Adapter<JobRecycleAdapter.JobViewHolder> {
    private ArrayList<Job> jobArrayList;
    private Context mContext;
    private int tUserId;
    private SavedJobDao savedJobDao;


    public JobRecycleAdapter(ArrayList<Job> jobArrayList, Context context, int tUserId, SavedJobDao savedJobDao){
        this.jobArrayList = jobArrayList;
        mContext = context;
        this.tUserId = tUserId;
        this.savedJobDao = savedJobDao;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_job, parent,false);
        JobViewHolder jobEVH = new JobViewHolder(view);
        return jobEVH;
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobArrayList.get(position);
        if (job.getCompany_logo_url() != null){
            Glide.with(mContext)
                    .asBitmap()
                    .load(job.getCompany_logo_url())
                    .into(holder.ivLogo);
        }
        else{
            holder.ivLogo.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.tvTitle.setText(job.getTitle());
        holder.tvCompany.setText(job.getCompanyName());
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Job job = jobArrayList.get(position);
                SavedJobs sj = new SavedJobs(tUserId, job.getTitle(), job.getCompanyName(), job.getJob_type(), job.getDescription(), job.getSalary());
                savedJobDao.insert(sj);
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                intent.putExtra("job", job);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivLogo;
        public TextView tvTitle;
        public TextView tvCompany;
        public Button btnAdd;
        public RelativeLayout parentLayout;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.ivLogo);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }

}
