package com.tudorfilp.marcopolomanagement.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public final class GoogleAuthHandler implements ProviderAuthHandler {

    private static final int GOOGLE_SIGN_IN_REQ_CODE = 1;
    private static GoogleAuthHandler authHandler;
    private static GoogleSignInAccount account;
    private GoogleSignInClient googleSignInClient;
    private GoogleApiClient googleApiClient;

    private GoogleAuthHandler(final Context context, AppCompatActivity activity, GoogleSignInOptions gso){

        googleSignInClient = GoogleSignIn.getClient(context, gso);
        googleApiClient = new GoogleApiClient.Builder(context).enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
    }


    @Override
    public void signIn(final CompletionCallBack callBack){

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            callBack.onSuccess();
                        } else {
                            callBack.onFailure(task.getException());
                        }
                    }
                });


    }

    public static GoogleAuthHandler getNewAuthHandler(Context context, AppCompatActivity activity,@NonNull GoogleSignInOptions gso) {

        authHandler = new GoogleAuthHandler(context, activity  , gso);

        return authHandler;
    }

    public static GoogleAuthHandler getAuthHandler(){

        return authHandler;
    }

    public static void setAccount(GoogleSignInAccount account){
        GoogleAuthHandler.account = account;
    }


    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public static int getGoogleSignInReqCode() {
        return GOOGLE_SIGN_IN_REQ_CODE;
    }

}
