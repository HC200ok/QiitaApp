package com.example.firstapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.firstapplication.bean.User;
import com.example.firstapplication.constants.QiitaConstants;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.OkHttp3Utils;
import com.example.firstapplication.util.SPUtil;
import com.example.firstapplication.util.URLUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends Activity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NavBar.init(LoginActivity.this, false, "ログイン", "close");
        initWebView();
    }

    private void initWebView() {
        webView = findViewById(R.id.webview);
        webView.loadUrl(QiitaConstants.AUTH_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code")) {
                    Map<String, String> mapRequest = URLUtil.getRequestParamMap(url);
                    if (mapRequest != null && mapRequest.size() != 0) {
                        String code = mapRequest.get("code");
                        Login(code);
                    }
                }
                return false;
            }
        });
    }

    private void Login(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", QiitaConstants.CLIENT_ID);
        map.put("client_secret", QiitaConstants.CLIENT_SECRET);
        map.put("code", code);

        OkHttp3Utils.doPost(QiitaConstants.TOKEN_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String token = "Bearer " + jsonObject.getString("token");
                        SPUtil.saveToken(LoginActivity.this,token);
                        UserHelper.getInstance().setToken(token);
                        initUserInfo();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    });
                }
            }
        });
    }
}
