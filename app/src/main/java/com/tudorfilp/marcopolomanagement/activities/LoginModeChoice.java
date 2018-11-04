package com.tudorfilp.marcopolomanagement.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.CompletionCallBack;
import com.tudorfilp.marcopolomanagement.classes.FacebookAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.FirebaseDatabaseUserAccountHandler;
import com.tudorfilp.marcopolomanagement.classes.GoogleAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.User;

import java.util.Arrays;

public class LoginModeChoice extends AppCompatActivity {

    private static final int GOOGLE_SIGN_IN_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mode_choice);

        prepareForGoogleLogin();
        prepareForFacebookLogin();

    }

    private void prepareForGoogleLogin(){
        final LoginModeChoice activity = new LoginModeChoice();
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

                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        updateProvidersAccountDetails();
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
                        .logInWithReadPermissions(LoginModeChoice.this, Arrays.asList("public_profile", "email"));
            }
        });
    }

    private void updateProvidersAccountDetails(){

        FirebaseUser user = FirebaseAuthHandler.getHandler().getmAuth().getCurrentUser();
        if(user!=null){
            User currentUser = new User();
            for(UserInfo profile : user.getProviderData()){
                currentUser.setEmail(profile.getEmail());
                currentUser.setProfileName(profile.getDisplayName());
                currentUser.setPhotoUrl(profile.getPhotoUrl().toString());
                currentUser.setLogin_mode_id(profile.getProviderId());
            }

            currentUser.save(new FirebaseDatabaseUserAccountHandler(), new CompletionCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    private void signInWithGoogle(final GoogleSignInClient googleSignInClient){

        Intent googleSignInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_REQ_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                //Toast.makeText(this, "something", Toast.LENGTH_SHORT).show();
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    GoogleAuthHandler.setAccount(account);
                    GoogleAuthHandler.getAuthHandler().signIn(new CompletionCallBack() {
                        @Override
                        public void onSuccess() {
                            //Toast.makeText(LoginModeChoice.this, "Succesful", Toast.LENGTH_SHORT).show();
                            updateProvidersAccountDetails();
                            startActivity(new Intent(getApplicationContext(), UserActivity.class));

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



}
