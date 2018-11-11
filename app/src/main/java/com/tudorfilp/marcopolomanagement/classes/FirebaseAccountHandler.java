package com.tudorfilp.marcopolomanagement.classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class FirebaseAccountHandler implements AccountHandler {

    public FirebaseAccountHandler(){


    }

    @Override
    public User getUserDetails() {

        User currentUser = new User();

        FirebaseUser user = FirebaseAuthHandler.getHandler().getmAuth().getCurrentUser();

        if(user!=null){
            for(UserInfo profile : user.getProviderData()){
                currentUser.setEmail(profile.getEmail());
                currentUser.setProfileName(profile.getDisplayName());
                currentUser.setPhotoUrl(profile.getPhotoUrl().toString());
                currentUser.setLogin_mode_id(profile.getProviderId());
            }
        }

        return currentUser;
    }

    @Override
    public void updateEmail(String email) {

    }

    @Override
    public void updatePassword(String password, CompletionCallBack callBack) {

    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public boolean isSignedIn() {
        return false;
    }
}
