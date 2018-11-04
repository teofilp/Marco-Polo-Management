package com.tudorfilp.marcopolomanagement.classes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseUserAccountHandler implements DatabaseUserAccountHandler {

    @Override
    public void save(User user, final CompletionCallBack callBack) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuthHandler.getHandler().getmAuth().getCurrentUser().getUid()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError == null)
                    callBack.onSuccess();
                else
                    callBack.onFailure(databaseError.toException());
            }
        });
    }

    @Override
    public User getUser(String id) {
        return null;
    }
}
