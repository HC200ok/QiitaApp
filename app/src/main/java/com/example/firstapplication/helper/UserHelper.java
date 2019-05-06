package com.example.firstapplication.helper;

import android.text.TextUtils;

import com.example.firstapplication.bean.User;

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
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Boolean isLogin() {
        return !TextUtils.isEmpty(token);
    }

    public void setToken(String phone) {
        this.token = phone;
    }
}