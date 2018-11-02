package com.tudorfilp.marcopolomanagement.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.AuthCompletionCallBack;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;

public class AppLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        startActivity(new Intent(getApplicationContext(), LoginModeChoice.class));
    }
}
