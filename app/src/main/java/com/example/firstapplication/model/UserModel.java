package com.example.firstapplication.model;

import io.realm.RealmObject;

public class UserModel extends RealmObject {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
