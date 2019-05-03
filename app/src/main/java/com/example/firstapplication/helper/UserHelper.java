package com.example.firstapplication.helper;

public class UserHelper {

    private static UserHelper instance;

    private UserHelper () {}

    public static UserHelper getInstance() {
        if (instance == null) {
            synchronized (UserHelper.class) {
                if (instance == null) {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String phone) {
        this.token = phone;
    }
}