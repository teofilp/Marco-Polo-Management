package com.tudorfilp.marcopolomanagement.classes;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public final class FirebaseAuthHandler implements AuthHandler{

    private static FirebaseAuthHandler handler;
    private FirebaseAuth mAuth;

    private FirebaseAuthHandler(){

    }

    @Override
    public void signIn(@NonNull String email,@NonNull String password, final CompletionCallBack callBack) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callBack.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onFailure(e);
                    }
                });
    }

    @Override
    public void register(String email, String password, final CompletionCallBack callBack){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    public static FirebaseAuthHandler getHandler() {

        if(handler == null)
            handler = new FirebaseAuthHandler();

        return handler;
    }

    public FirebaseAuth getmAuth() {
        if(mAuth == null)
            mAuth = FirebaseAuth.getInstance();

        return mAuth;
    }
}
