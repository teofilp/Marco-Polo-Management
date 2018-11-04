package com.tudorfilp.marcopolomanagement.classes;

import android.support.annotation.NonNull;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

public final class FacebookAuthHandler implements ProviderAuthHandler {

    private static CallbackManager callbackManager;
    private static FacebookAuthHandler facebookAuthHandler;
    private String token;

    private FacebookAuthHandler(){
        if(callbackManager == null)
            callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void signIn(final CompletionCallBack callBack) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        FirebaseAuthHandler.getHandler().getmAuth().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    public static FacebookAuthHandler getFacebookAuthHandler(){
        if(facebookAuthHandler == null)
            facebookAuthHandler = new FacebookAuthHandler();

        return facebookAuthHandler;
    }

    public static CallbackManager getCallbackManager(){

        if(callbackManager == null)
            callbackManager = CallbackManager.Factory.create();

        return callbackManager;
    }

    public void setToken(String token){
        this.token = token;
    }


}
