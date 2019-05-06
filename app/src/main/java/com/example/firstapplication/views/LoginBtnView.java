package com.example.firstapplication.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.firstapplication.LoginActivity;
import com.example.firstapplication.R;

public class LoginBtnView extends LinearLayout {

    private View mView;
    private Button mButton;

    Drawable borderColor;
    int textColor;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public LoginBtnView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public LoginBtnView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public LoginBtnView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoginBtnView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init(final Context context, AttributeSet attrs) {
        if (attrs == null) return;

        final Activity activity = (Activity) context;

//        获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.loginBtnView);
        borderColor = typedArray.getDrawable(R.styleable.loginBtnView_border_color);
        textColor = typedArray.getInt(R.styleable.loginBtnView_text_color, R.color.white);
        typedArray.recycle();

//        绑定layout布局
        mView = LayoutInflater.from(context).inflate(R.layout.login_btn_view, this, false);
        mButton = mView.findViewById(R.id.login_btn);

//        布局关联属性
        mButton.setTextColor(textColor);
        mButton.setBackground(borderColor);
        mButton.setPadding(50, 10, 50, 10);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(v.getContext(), LoginActivity.class));
                activity.overridePendingTransition(R.anim.activity_open,0);
            }
        });

        addView(mView);
    }
}
