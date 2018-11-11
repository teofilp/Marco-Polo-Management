package com.tudorfilp.marcopolomanagement.activities;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.tudorfilp.marcopolomanagement.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initializeViews();
    }

    void initializeViews(){
        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("finish_landing_activity")){
                    finish();
                }
            }
        };

        final View sharedAppLogo = findViewById(R.id.app_logo);
        final View sharedAppTitle = findViewById(R.id.app_title);

        findViewById(R.id.requestLoginPageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerReceiver(broadcastReceiver, new IntentFilter(("finish_landing_activity")));

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LandingActivity.this,
                        Pair.create(sharedAppLogo, "app_logo_transition"),
                        Pair.create(sharedAppTitle, "app_title_transition"));

                startActivity(new Intent(getApplicationContext(), Login.class), options.toBundle());
            }
        });

        findViewById(R.id.requestRegisterPageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerReceiver(broadcastReceiver, new IntentFilter("finish_landing_activity"));

            }
        });
    }
}
