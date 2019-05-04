package com.example.firstapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.SPUtil;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        NavBar.init(SettingActivity.this, false, "設定", "close");
        init();
    }

    private void init() {
        TextView tvLogout = findViewById(R.id.logout);

        if (UserHelper.getInstance().isLogin()) {
            tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserHelper.getInstance().setToken("");
                    SPUtil.removeToken(SettingActivity.this);
                    onBackPressed();
                }
            });
        } else {
            tvLogout.setVisibility(View.GONE);
        }

    }
}
