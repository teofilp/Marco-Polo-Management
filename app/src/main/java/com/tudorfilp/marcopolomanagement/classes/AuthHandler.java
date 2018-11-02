package com.tudorfilp.marcopolomanagement.classes;

public interface AuthHandler {
    void signIn(String email, String password, AuthCompletionCallBack callBack);
    void signOut();
    boolean isSignedIn();
}
