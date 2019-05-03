package com.example.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.firstapplication.constants.OauthConstants;
import com.example.firstapplication.util.SPUtil;
import com.example.firstapplication.util.URLUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

        initWebView();
    }

    private void initWebView() {
        webView = findViewById(R.id.webview);
        webView.loadUrl(OauthConstants.AUTH_URL);
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
        map.put("client_id", OauthConstants.CLIENT_ID);
        map.put("client_secret", OauthConstants.CLIENT_SECRET);
        map.put("code", code);

        OkHttp3Utils.doPost(OauthConstants.TOKEN_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String token = "Bearer " + jsonObject.getString("token");
                        SPUtil.saveToken(LoginActivity.this,token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void closeLogin() {
        this.overridePendingTransition(R.anim.activity_close,0);
    }
}
