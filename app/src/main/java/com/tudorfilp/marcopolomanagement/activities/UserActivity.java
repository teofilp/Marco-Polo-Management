package com.tudorfilp.marcopolomanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.AccountHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAccountHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initializeViews();
    }

    private void initializeViews(){
        findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(new FirebaseAccountHandler());
            }
        });
    }

    private void signOut(AccountHandler accountHandler){

        accountHandler.signOut();
        finish();
    }
}
