package com.example.firstapplication;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp3Utils {
    private static OkHttpClient okHttpClient = null;

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
        OkHttpClient okHttpClient = getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void doPost(String url, Map<String,String> map, Callback callback){
        OkHttpClient okHttpClient = getOkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合   设置请求体
        for (String mapKey : map.keySet()){
            builder.add(mapKey,map.get(mapKey));
        }
        //设置请求方式
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        //执行请求方式    接口回调
        okHttpClient.newCall(request).enqueue(callback);
    }
}
