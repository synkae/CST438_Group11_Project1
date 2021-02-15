package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;
import com.codepath.synkae.cst438_proj1.models.Categories;
import com.codepath.synkae.cst438_proj1.models.Category;
import com.codepath.synkae.cst438_proj1.models.Job;
import com.codepath.synkae.cst438_proj1.models.JobSearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity{
    private Spinner spinner;
    private EditText etKeyword;
    private Button btnSearch;
    private EditText etCompany;
    private int tUserId = -1; //user identification to pass around
    private DAO DAO;
    private User tUser;
    private static final String TAG = "SearchActivity";
    private static final String BASE_URL = "https://remotive.io/";
    private ArrayList<Category> categoryArrayList = new ArrayList<Category>();
    ArrayAdapter<Category> spinnerAdapter;
    private RecyclerView rvJobs;
    private JobRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spinner = findViewById(R.id.spinner);
        etKeyword = findViewById(R.id.etKeyword);
        btnSearch = findViewById(R.id.btnSearch);
        etCompany = findViewById(R.id.etCompany);
        rvJobs = findViewById(R.id.rvJobs);
        //get user id and load the user
        getDatabase();
        tUserId = getIntent().getIntExtra("userIdKey", -1);
        tUser = DAO.getUserByUserId(tUserId);
        //loads all job categories from API endpoint to spinner
        loadCategories();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etKeyword.getText().toString().isEmpty() || spinner.getSelectedItem().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Some input is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String keyword = etKeyword.getText().toString();
                Category c = (Category)spinner.getSelectedItem();
                String company = etCompany.getText().toString();
                String category = c.getSlug();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://remotive.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RemotiveAPI remotiveAPI = retrofit.create(RemotiveAPI.class);
                Call<JobSearch> call = remotiveAPI.searchJobs(keyword, category, company,20);
                call.enqueue(new Callback<JobSearch>() {
                    @Override
                    public void onResponse(Call<JobSearch> call, Response<JobSearch> response) {
                        if(!response.isSuccessful()){
                            Log.i(TAG, response.toString());
                        }
                        Log.d(TAG, "onResponse: Server Response: " + response.toString());
                        Log.d(TAG, "onResponse: Server Response: " + response.body().toString());
                        ArrayList<Job> jobsList = response.body().getJobsList();
                        Log.d(TAG, "PRINTING ALL JOBS FROM API");
                        for(Job j : jobsList){
                            Log.d(TAG, "id: " + j.getId() + " url: " + j.getUrl() + " title: " + j.getTitle() + " company name: " + j.getCompanyName());
                        }
                        initRecyclerView(jobsList);
                    }

                    @Override
                    public void onFailure(Call<JobSearch> call, Throwable t) {
                        Log.e(TAG, t.toString());
                        Toast.makeText(SearchActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadFromUserProfile() {
        Log.d(TAG, "Username: " + tUser.getUsername() + " company: " + tUser.getCompany_name() + " category: " + tUser.getCategory());
        String company = tUser.getCompany_name();
        etCompany.setText(company);
        String category = tUser.getCategory();
        if (tUser.getCategory().isEmpty()){
            return;
        }
        Category temp = null;
        for (Category c : categoryArrayList){
            if(c.getSlug().equals(category)){
                temp = c;
            }
        }
        int pos = spinnerAdapter.getPosition(temp);
        spinner.setSelection(pos);
    }


    private void initRecyclerView(ArrayList<Job> jobList){
        adapter = new JobRecycleAdapter(jobList, this);
        rvJobs.setHasFixedSize(true);
        rvJobs.setAdapter(adapter);
        rvJobs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    /*
     * Loads the dropdown menu
     */
    private void loadDropDown() {
        spinnerAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }
    /*
     * Calls the RemotiveAPI and stores the job categories into ArrayList<Category> categoryArrayList
     */
    private void loadCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://remotive.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RemotiveAPI remotiveAPI = retrofit.create(RemotiveAPI.class);
        Call<Categories> call = remotiveAPI.getCategories();
        Log.d(TAG, call.toString());
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, response.toString());
                }
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
                Log.d(TAG, "onResponse: Server Response: " + response.body().toString());
                categoryArrayList = response.body().getCategoryList();
                //for testing purposes
                for (Category cat : response.body().getCategoryList()){
                    Log.d(TAG, "Name: " + cat.getName() + " id: " + cat.getId());
                }
                loadDropDown();
                loadFromUserProfile();
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(SearchActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Static method that does the factory thingy from one activity to go to this activity
     * @params Context
     * @return Intent
     */
    public static Intent searchActivityIntent(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

}