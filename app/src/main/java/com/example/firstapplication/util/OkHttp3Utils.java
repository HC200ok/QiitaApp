package com.example.firstapplication.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.firstapplication.helper.UserHelper;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttp3Utils {
    private static OkHttpClient okHttpClient = null;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public OkHttp3Utils() {
    }

    private static OkHttpClient getOkHttpClient() {
        synchronized (OkHttp3Utils.class) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient();
            }
        }
        return okHttpClient;
    }

    public static void doGet(String url, Callback callback){
        String token = UserHelper.getInstance().getToken();
        OkHttpClient okHttpClient = getOkHttpClient();
        Request request;

        if (TextUtils.isEmpty(token)) {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", token)
                    .build();
        }
        okHttpClient.newCall(request).enqueue(callback);
    }


    public static void doPost(String url, Map<String,String> map, Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", UserHelper.getInstance().getToken())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
