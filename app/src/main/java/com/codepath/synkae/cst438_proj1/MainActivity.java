package com.codepath.synkae.cst438_proj1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "userIdKey";
    private static final String PREFERENCES_KEY = "preferencesKey";

    private com.codepath.synkae.cst438_proj1.db.DAO DAO;
    private int tUserId = -1; //user identification to pass around
    private SharedPreferences sPreferences = null;
    private User tUser;

    private Button viewList; //Not hooked up yet!!!
    private Button searchButton;
    private Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.searchButton);
        profileButton = findViewById(R.id.profileButton);
        getDatabase();
        checkForUser();
        addUserToPreference(tUserId);
        loginUser(tUserId);

        menuDisplay();
    }

    protected void onResume() {
        super.onResume();
    }

    public static Intent mainActivityIntent(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void getDatabase() {
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getDAO();
    }

    private void checkForUser() {
        //do we have user in intent?
        tUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //in preferences?
        if (tUserId != -1) {
            return;
        }

        if (sPreferences == null) {
            getPrefs();
        }
        tUserId = sPreferences.getInt(USER_ID_KEY, -1);

        //any at all?
        if (tUserId != -1) {
            return;
        }
        List<User> users = DAO.getAllUsers();
        if (users.size() <= 0) {
            User TstUser = new User("user", "pass");
            DAO.insert(TstUser);
        }

        Intent intent = LoginActivity.loginActivityIntent(this);
        startActivity(intent);
    }

    private void getPrefs() {
        sPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if (sPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    private void loginUser(int userId) {
        tUser = DAO.getUserByUserId(userId);
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
        tUserId = -1;
        checkForUser();

    }

    private void menuDisplay() {
        Button viewListButton = findViewById(R.id.viewListButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra(USER_ID_KEY, tUserId);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchActivity.searchActivityIntent(v.getContext());
                intent.putExtra(USER_ID_KEY, tUserId);
                startActivity(intent);
            }
        });


    }
}