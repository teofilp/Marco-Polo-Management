package com.tudorfilp.marcopolomanagement.classes;

public interface AuthHandler {
    void signIn(String email, String password, CompletionCallBack callBack);
    void register(String email, String password, CompletionCallBack callBack);
}
