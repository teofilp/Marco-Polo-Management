package com.tudorfilp.marcopolomanagement.classes;

public interface AccountHandler {
    User getUserDetails();
    void updateEmail(String email);
    void updatePassword(String password, CompletionCallBack callBack);
    void signOut();
    boolean isSignedIn();
}
