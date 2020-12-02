package com.example.kanbanmobile.models;

import com.example.kanbanmobile.enums.UserType;

public class User {
    String login;
    String password;
    UserType userType;

    public User(String login, UserType userType){
        this.login = login;
        this.userType = userType;
    }

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}
