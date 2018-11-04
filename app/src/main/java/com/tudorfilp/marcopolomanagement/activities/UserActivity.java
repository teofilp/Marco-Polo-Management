package com.tudorfilp.marcopolomanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

    }

    public void signOut(View view){

        FirebaseAuthHandler.getHandler().signOut();
        SharedPreferences sharedPreferences = this.getSharedPreferences("login_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("login_mode").apply();
        finish();
    }
}
