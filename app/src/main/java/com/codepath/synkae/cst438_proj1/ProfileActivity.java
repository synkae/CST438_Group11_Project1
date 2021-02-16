package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;
import com.codepath.synkae.cst438_proj1.models.Categories;
import com.codepath.synkae.cst438_proj1.models.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private static final String BASE_URL = "https://remotive.io/";
    private ArrayList<SpinnerCatSplit> dropDownList = new ArrayList<SpinnerCatSplit>();

    private int tUserId; //user identification to pass around
    private SharedPreferences sPreferences = null;
    private User tUser;
    private String tCat;
    private DAO DAO;

    private Spinner dropDownJobCat;
    private TextView jCatSelected;
    private TextView compSelected;
    private EditText compUserInput;
    private Button goBackBtn;
    private Button updatePro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dropDownJobCat = findViewById(R.id.jCatDrpDwn);
        jCatSelected = findViewById(R.id.jCatSel);
        compSelected = findViewById(R.id.jCompSel);
        compUserInput = findViewById(R.id.jCompET);
        goBackBtn = findViewById(R.id.goBackButton);
        updatePro = findViewById(R.id.upProButton);

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
        loadCategories();

        dropDownJobCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCatSplit scs = (SpinnerCatSplit) parent.getItemAtPosition(position);
                tCat = scs.slug;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getDatabase();
        tUserId = getIntent().getIntExtra("userIdKey", -1);
        tUser = DAO.getUserByUserId(tUserId);

        updatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tUser.setCompany_name(compUserInput.getText().toString());
                tUser.setCategory(tCat);
                DAO.update(tUser);
                compSelected.setText(tUser.getCompany_name());
                switch (tUser.getCategory()) {
                    case "software-dev":
                        jCatSelected.setText(R.string.switch1);
                        break;
                    case "customer-support":
                        jCatSelected.setText(R.string.switch2);
                        break;
                    case "design":
                        jCatSelected.setText(R.string.switch3);
                        break;
                    case "marketing":
                        jCatSelected.setText(R.string.switch4);
                        break;
                    case "sales":
                        jCatSelected.setText(R.string.switch5);
                        break;
                    case "product":
                        jCatSelected.setText(R.string.switch6);
                        break;
                    case "business":
                        jCatSelected.setText(R.string.switch7);
                        break;
                    case "data":
                        jCatSelected.setText(R.string.switch8);
                        break;
                    case "devops":
                        jCatSelected.setText(R.string.switch9);
                        break;
                    case "finance-legal":
                        jCatSelected.setText(R.string.switch10);
                        break;
                    case "hr":
                        jCatSelected.setText(R.string.switch11);
                        break;
                    case "qa":
                        jCatSelected.setText(R.string.switch12);
                        break;
                    case "teaching":
                        jCatSelected.setText(R.string.switch13);
                        break;
                    case "writing":
                        jCatSelected.setText(R.string.switch14);
                        break;
                    case "medical-health":
                        jCatSelected.setText(R.string.switch15);
                        break;
                    case "all-others":
                        jCatSelected.setText(R.string.switch16);
                        break;

                }
            }
        });

        jCatSelected.setText(tUser.getCategory());
        compSelected.setText(tUser.getCompany_name());
    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    private void loadDropDown() {
        ArrayAdapter<SpinnerCatSplit> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropDownList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownJobCat.setAdapter(spinnerAdapter);
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
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                for (Category cat : response.body().getCategoryList()){
                    dropDownList.add(new SpinnerCatSplit(cat.getName(), cat.getSlug()));
                }
                loadDropDown();
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}