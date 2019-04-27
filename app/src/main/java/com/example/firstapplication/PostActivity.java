package com.example.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {
    public static final String POST_BODY = "postBody";
    String post_body;
    TextView tvPostBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initView();
    }

    private void initView() {
        post_body = getIntent().getStringExtra(POST_BODY);
        tvPostBody = findViewById(R.id.post_body);
        tvPostBody.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvPostBody.setText(Html.fromHtml(post_body));
    }
}
