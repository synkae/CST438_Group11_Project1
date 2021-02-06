package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "userIdKey";
    private static final String PREFERENCES_KEY = "preferencesKey";

    private com.codepath.synkae.cst438_proj1.db.DAO DAO;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
    }

    protected void onResume() {
        super.onResume();
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    private void checkForUser() {
        //do we have user in intent?
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //in preferences?
        if (mUserId != -1) {
            return;
        }

        if (mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        //any at all?
        if (mUserId != -1) {
            return;
        }
        List<User> users = DAO.getAllUsers();
        if (users.size() <= 0) {
            User TstAdmin = new User("Admin", "pass");
            User TstUser = new User("User", "pass");
            DAO.insert(TstAdmin, TstUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    private void loginUser(int userId) {
        mUser = DAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutM) {
            logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    private void logoutUser() {
        clearUserFromIntent();
        clearUserFromPref();
        mUserId = -1;
        checkForUser();

    }
}