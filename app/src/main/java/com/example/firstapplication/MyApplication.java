package com.example.firstapplication;

import android.app.Application;

import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.SPUtil;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String token = SPUtil.getToken(this);
        UserHelper.getInstance().setToken(token);
    }
}
