package com.tudorfilp.marcopolomanagement.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tudorfilp.marcopolomanagement.R;
import com.tudorfilp.marcopolomanagement.classes.AuthCompletionCallBack;
import com.tudorfilp.marcopolomanagement.classes.FirebaseAuthHandler;
import com.tudorfilp.marcopolomanagement.classes.GoogleAuthHandler;

public class LoginModeChoice extends AppCompatActivity {

    private static final int GOOGLE_SIGN_IN_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mode_choice);

        prepareForGoogleLogin();

    }

    private void signInWithGoogle(final GoogleSignInClient googleSignInClient){

            Intent googleSignInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN_REQ_CODE);
    }

    public void prepareForGoogleLogin(){
        final LoginModeChoice activity = new LoginModeChoice();
        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle(GoogleAuthHandler.getAuthHandler(getApplicationContext(), activity, gso).getGoogleSignInClient());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_REQ_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                //Toast.makeText(this, "something", Toast.LENGTH_SHORT).show();
                GoogleSignInAccount account = task.getResult(ApiException.class);

                    FirebaseAuthHandler.getHandler().authWithGoogle(account, new AuthCompletionCallBack() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginModeChoice.this, "Logged in with google", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(LoginModeChoice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

            } catch(ApiException e){
                Log.i("error from apiException", e.toString());
            }
        }
    }
}
