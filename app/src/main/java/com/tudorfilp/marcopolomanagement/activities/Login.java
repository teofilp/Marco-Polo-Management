package com.tudorfilp.marcopolomanagement.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.AccountHandler;
import com.tudorfilp.marcopolomanagement.classes.AuthHandler;
import com.tudorfilp.marcopolomanagement.classes.CompletionCallBack;
import com.tudorfilp.marcopolomanagement.classes.DatabaseUserAccountHandler;
import com.tudorfilp.marcopolomanagement.classes.FacebookAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAccountHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseDatabaseUserAccountHandler;
import com.tudorfilp.marcopolomanagement.classes.GoogleAuthHandler;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        prepareForGoogleLogin();
        prepareForFacebookLogin();

    }

    private void initializeViews() {
        findViewById(R.id.default_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAttempt(FirebaseAuthHandler.getHandler(), new CompletionCallBack(){
                    @Override
                    public void onSuccess() {
                        goToLoggedInActivity();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }
        });
    }

    void loginAttempt(AuthHandler authHandler, CompletionCallBack completionCallBack){

        String email = ((AppCompatEditText) findViewById(R.id.login_email)).getText().toString();
        String password = ((AppCompatEditText) findViewById(R.id.login_password)).getText().toString();
        if(!email.equals("") && !password.equals(""))
            authHandler.signIn(email, password, completionCallBack);

    }

    private void prepareForGoogleLogin(){
        final Login activity = new Login();
        Button googleSignInButton = findViewById(R.id.google_sign_in_button);

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        final GoogleAuthHandler googleAuthHandler = GoogleAuthHandler.getNewAuthHandler(getApplicationContext(), activity, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle(googleAuthHandler.getGoogleSignInClient());
            }
        });
    }

    private void prepareForFacebookLogin(){
        Button facebookSignInButton = findViewById(R.id.facebook_sign_in_button);

        LoginManager.getInstance().registerCallback(FacebookAuthHandler.getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                FacebookAuthHandler.getFacebookAuthHandler().setToken(loginResult.getAccessToken().getToken());
                FacebookAuthHandler.getFacebookAuthHandler().signIn(new CompletionCallBack() {
                    @Override
                    public void onSuccess() {

                        goToLoggedInActivity();
                        updateProvidersAccountDetails(new FirebaseAccountHandler(), new FirebaseDatabaseUserAccountHandler());

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        facebookSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance()
                        .logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email"));
            }
        });
    }

    private void updateProvidersAccountDetails(AccountHandler accountHandler, DatabaseUserAccountHandler databaseUserAccountHandler){

        accountHandler.getUserDetails().save(databaseUserAccountHandler, new CompletionCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    private void signInWithGoogle(final GoogleSignInClient googleSignInClient){

        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, GoogleAuthHandler.getGoogleSignInReqCode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GoogleAuthHandler.getGoogleSignInReqCode()){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{

                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    GoogleAuthHandler.setAccount(account);

                    GoogleAuthHandler.getAuthHandler().signIn(new CompletionCallBack() {
                        @Override
                        public void onSuccess() {

                            goToLoggedInActivity();
                            updateProvidersAccountDetails(new FirebaseAccountHandler(), new FirebaseDatabaseUserAccountHandler());

                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });

            } catch(ApiException e){

                Log.i("error from apiException", e.toString());

            }
        } else {
            FacebookAuthHandler.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void goToLoggedInActivity(){

        sendBroadcast(new Intent("finish_landing_activity"));
        startActivity(new Intent(getApplicationContext(), UserActivity.class));
        finish();

    }


}
