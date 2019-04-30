package com.example.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.firstapplication.bean.Posts;
import com.example.firstapplication.constants.OauthConstants;
import com.example.firstapplication.helper.RealmHelper;
import com.example.firstapplication.model.UserModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView = findViewById(R.id.webview);

        webView.loadUrl(OauthConstants.AUTH_URL);
        initWebView();
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("URL", "LOAD URL:" + url);

                Log.d("has_code", "has_code" + url.contains("code"));
                return false;
            }
        });
    }

    private void Login(String code) {
        String token;
        Map<String, String> map = new HashMap<String, String>();
        map.put("client_id", OauthConstants.CLIENT_ID);
        map.put("client_secret", OauthConstants.CLIENT_SECRET);
        map.put("code", code);

        OkHttp3Utils.doPost(OauthConstants.TOKEN_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Gson解析
                Gson gson = new Gson();
                String posts_string = "{results:" + response.body().string() + "}";
                Posts posts = gson.fromJson(posts_string, Posts.class);
                if (response.isSuccessful()) {

                }

            }
        });


        UserModel userModel = new UserModel();
        userModel.setToken(token);

        new RealmHelper().saveUser(userModel);

    }

    private void closeLogin() {
        this.overridePendingTransition(R.anim.activity_close,0);
    }
}
