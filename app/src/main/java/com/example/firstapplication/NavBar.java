package com.example.firstapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NavBar {

    public static void init(final Activity activity, boolean isShowBack, String title, String type) {
        ImageView mIvBack = activity.findViewById(R.id.iv_back);
        TextView mTvTitle = activity.findViewById(R.id.tv_title);
        ImageView mIvSetting = activity.findViewById(R.id.setting);
        ImageView mIvClose = activity.findViewById(R.id.close);

        switch(type) {
            case "setting":
                mIvSetting.setVisibility(View.VISIBLE);
                mIvClose.setVisibility(View.GONE);
                break;
            case "close":
                mIvSetting.setVisibility(View.GONE);
                mIvClose.setVisibility(View.VISIBLE);
                break;
                default:
                    mIvSetting.setVisibility(View.GONE);
                    mIvClose.setVisibility(View.GONE);
                break;
        }

        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);

        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        mIvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SettingActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_open,0);
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }
}
