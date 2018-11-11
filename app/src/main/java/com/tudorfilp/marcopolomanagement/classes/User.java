package com.tudorfilp.marcopolomanagement.classes;

public class User {
    private String id;
    private String profileName;
    private String email;
    private String login_mode_id;

    public User(){

    }

    public String getId(){ return id;}

    public void setId(String id) { this.id = id; }

    public String getLogin_mode_id() {
        return login_mode_id;
    }

    public void setLogin_mode_id(String login_mode_id) {
        this.login_mode_id = login_mode_id;
    }


    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void save(DatabaseUserAccountHandler handler, CompletionCallBack callBack){
        handler.save(this, callBack);
    }

    private String photoUrl;
}
