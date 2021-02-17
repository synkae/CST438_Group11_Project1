package com.codepath.synkae.cst438_proj1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bumptech.glide.Glide;
import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;
import com.codepath.synkae.cst438_proj1.models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobRecycleAdapter extends RecyclerView.Adapter<JobRecycleAdapter.JobViewHolder> {
    private ArrayList<Job> jobArrayList;
    private Context mContext;
    private int uid;
    private DAO DAO;
    private String select;

    public JobRecycleAdapter(ArrayList<Job> jobArrayList, int uid, String select, Context context){
        this.jobArrayList = jobArrayList;
        mContext = context;
        this.uid = uid;
        this.select = select;
        getDatabase();
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
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                intent.putExtra("job", job);
                mContext.startActivity(intent);
            }
        });
        if (select.equals("search")){
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Job tJob = jobArrayList.get(position);
                    tJob.setMUserId(uid);
                    DAO.insert(tJob);
                    Toast.makeText(view.getContext(), "Job added to list!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            holder.btnAdd.setText("Delete");
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Job tJob = jobArrayList.get(position);
                    DAO.deleteSjob(tJob.getSaveJId());
                    jobArrayList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(), "Job deleted from list!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder{
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

    public void getDatabase() {
        DAO = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }


}
