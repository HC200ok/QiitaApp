package com.example.firstapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.views.LoginBtnView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    private TextView withoutLogin;
    private LoginBtnView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        withoutLogin = findViewById(R.id.withoutLogin);
        loginBtn = findViewById(R.id.loginBtn);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        if (UserHelper.getInstance().isLogin()) {
            withoutLogin.setVisibility(View.GONE);
            loginBtn.setVisibility(View.GONE);
            countDown();
        } else {
            withoutLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            withoutLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toMain();
                }
            });
        }
    }

    private void countDown() {
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                toMain();
            }
        }, 2 * 1000);
    }

    private void toMain () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
