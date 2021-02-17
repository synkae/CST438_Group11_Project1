package com.codepath.synkae.cst438_proj1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.codepath.synkae.cst438_proj1.db.DAO;

public class LoginActivity extends AppCompatActivity {

    private EditText userBox;
    private EditText passBox;
    private String inputUn;
    private String inputPw;
    private User mUser;
    private DAO DAO;
    private User checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupDisplay();
        getDatabase();

    }

    private void wireupDisplay(){
        userBox = findViewById(R.id.textLoginUn);
        passBox = findViewById(R.id.textLoginPw);
        Button loginButton = findViewById(R.id.loginButton);
        Button createAccButton = findViewById(R.id.createAccButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        toastMaker("Invalid password");
                    }else{
                        Intent intent = MainActivity.mainActivityIntent(getApplicationContext(), mUser.getUserId());
                        startActivity(intent);
                    }
                }

            }
        });

        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                checkUser = DAO.getUserByUsername(inputUn);
                String created;

                if (inputUn.equals("")||inputPw.equals("")){
                    created = "You must enter a Username/Password";
                    toastMaker(created);
                }

                else if (checkUser != null){
                    created = "Account creation failed. Username already in use.";
                    toastMaker(created);
                }

                else {

                    User newUser = new User(inputUn, inputPw);
                    DAO.insert(newUser);
                    created = "Account: " + newUser.getUsername() + " has been created. Please Login.";
                    toastMaker(created);
                }
            }
        });


    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(inputPw);
    }

    private void getValuesFromDisplay(){
        inputUn = userBox.getText().toString();
        inputPw = passBox.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        mUser = DAO.getUserByUsername(inputUn);
        if(mUser == null){
            String s = "no user " + inputUn + " found";
            toastMaker(s);
            return false;
        }
        return true;
    }

    private void getDatabase(){
        DAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getDAO();
    }

    public static Intent loginActivityIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }

    private void toastMaker(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}