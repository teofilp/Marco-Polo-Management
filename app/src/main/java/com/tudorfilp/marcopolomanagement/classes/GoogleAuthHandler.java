package com.tudorfilp.marcopolomanagement.classes;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tudorfilp.marcopolomanagement.R;

public final class GoogleAuthHandler implements ProviderAuthHandler {
    static GoogleAuthHandler authHandler;

    private static Context context;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private GoogleApiClient googleApiClient;


    private GoogleAuthHandler(Context context, final AppCompatActivity activity, GoogleSignInOptions gso){

        GoogleAuthHandler.context = context;
        this.gso = gso;


        googleSignInClient = GoogleSignIn.getClient(context, gso);
        googleApiClient = new GoogleApiClient.Builder(context).enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(activity, "Connection error", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();
    }

    @Override
    public void signIn(){


    }

    public static GoogleAuthHandler getAuthHandler(Context context, AppCompatActivity activity, GoogleSignInOptions gso) {
        if(authHandler == null)
            authHandler = new GoogleAuthHandler(context, activity, gso);

        return authHandler;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }
}
