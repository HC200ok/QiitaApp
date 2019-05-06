package com.example.firstapplication;

import android.app.Application;

import com.example.firstapplication.bean.User;
import com.example.firstapplication.constants.QiitaConstants;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.OkHttp3Utils;
import com.example.firstapplication.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String token = SPUtil.getToken(this);
        UserHelper.getInstance().setToken(token);
        if (UserHelper.getInstance().isLogin()) {
            initUserInfo();
        }
    }

    private void initUserInfo() {
        String url = QiitaConstants.HOST +  "/authenticated_user";
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String res = response.body().string();
                    User user = gson.fromJson(res, User.class);
                    UserHelper.getInstance().setUser(user);
                }
            }
        });
    }
}
