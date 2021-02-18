package com.codepath.synkae.cst438_proj1;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;
import com.codepath.synkae.cst438_proj1.models.Categories;
import com.codepath.synkae.cst438_proj1.models.Category;
import com.codepath.synkae.cst438_proj1.models.Job;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private DAO Dao;
    private List<User> userArrayList;
    private ArrayList<String> allCategories;
    private List<Job> allJobs;
    @Before
    public void init(){
        Dao = Room.databaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getDAO();
        Dao.deleteAllSavedJobs();
        Dao.deleteAllUsers();
        Dao.insert(new User("1", "password"));
        userArrayList = new ArrayList<User>();
        allCategories = new ArrayList<String>();
        allJobs = new ArrayList<Job>();

        allCategories.add("software-dev");
        allCategories.add("customer-support");
        allCategories.add("design");
        allCategories.add("marketing");
        allCategories.add("sales");
        allCategories.add("design");
        allCategories.add("product");
        allCategories.add("business");
        allCategories.add("data");
        allCategories.add("devops");
        allCategories.add("finance-legal");
        allCategories.add("hr");
        allCategories.add("qa");
        allCategories.add("writing");
        allCategories.add("medical-health");
        allCategories.add("all-others");

        allJobs.add(new Job(1, "www.test.com", "example", "software", "exampleCompany", "full time", "17/2/2021", "", "10000", "description", ""));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.codepath.synkae.cst438_proj1", appContext.getPackageName());
    }

    @Test
    public void validUser(){
        userArrayList = Dao.getAllUsers();
        User test = new User("1", "password");
        boolean check = false;
        for (User u : userArrayList){
            if (u.getUsername().equals(test.getUsername()) && u.getPassword().equals(test.getPassword())){
                check = true;
            }
        }
        assertTrue(check);
    }
    @Test
    public void invalidUser(){
        userArrayList = Dao.getAllUsers();
        User actual = userArrayList.get(0);
        User test = new User("1", "wrongpass");
        boolean check = false;
        for (User u : userArrayList){
            if (u.getUsername().equals(test.getUsername()) && u.getPassword().equals(test.getPassword())){
                check = true;
            }
        }
        assertFalse(check);
    }

    @Test
    public void checkCategories(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://remotive.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RemotiveAPI remotiveAPI = retrofit.create(RemotiveAPI.class);
        Call<Categories> call = remotiveAPI.getCategories();
        final boolean[] check = {true};
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                ArrayList<Category> categoryArrayList = response.body().getCategoryList();
                //for testing purposes
                for (int i=0; i<categoryArrayList.size(); i++){
                    if (!categoryArrayList.get(i).getSlug().equals(allCategories.get(i))) {
                        check[0] = false;
                        break;
                    }
                }
            }
            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });
        assertTrue(check[0]);
    }

    @Test
    public void checkEmptyInput() {
        String inputUsername = "";
        String inputPassword = "";
        // User userEmpty = new User("", "");
        boolean checkInput = false;
        if(inputUsername == "" && inputPassword == "") {
            checkInput = true;
        }
        assertTrue(checkInput);
    }

    @Test
    public void checkJobs(){
    }


}