package com.tudorfilp.marcopolomanagement.classes;

public interface DatabaseUserAccountHandler {
    void save(User user, CompletionCallBack callBack);
    User getUser(String id);
}
