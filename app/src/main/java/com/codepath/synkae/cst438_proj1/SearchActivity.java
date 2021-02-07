package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText etKeyword;
    private Button btnSearch;
    private static final String TAG = "SearchActivity";
    private static final String BASE_URL = "https://remotive.io/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner = findViewById(R.id.spinner);
        etKeyword = findViewById(R.id.etKeyword);
        btnSearch = findViewById(R.id.btnSearch);
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
                //Categories cat = response.body();
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