package com.tudorfilp.marcopolomanagement.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.transition.Explode;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.CompletionCallBack;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        configureEnterAnimation();
    }

    private void initializeViews() {

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAttempt(FirebaseAuthHandler.getHandler(), new CompletionCallBack() {
                    @Override
                    public void onSuccess() {
                        toLoggedInActivity();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });

    }

    private void registerAttempt(FirebaseAuthHandler handler, CompletionCallBack callBack) {
        String email = ((AppCompatEditText) findViewById(R.id.register_email)).getText().toString();
        String password = ((AppCompatEditText) findViewById(R.id.register_password)).getText().toString();
        String passwordConfirmation = ((AppCompatEditText) findViewById(R.id.register_password_confirmation)).getText().toString();

        if(!email.equals("") && !password.equals("") && password.equals(passwordConfirmation))
            handler.register(email, password, callBack);
    }

    private void toLoggedInActivity() {
        startActivity(new Intent(getApplicationContext(), UserActivity.class));
        finish();
    }

    private void configureEnterAnimation() {

        Explode enterTransition = new Explode();
        enterTransition.setDuration(500);
        getWindow().setEnterTransition(enterTransition);
    }

}
