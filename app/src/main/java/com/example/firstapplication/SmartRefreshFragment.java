package com.example.firstapplication;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class SmartRefreshFragment extends Fragment {
    static {//static 代码段可以防止内存泄露
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "画面を引き下げて...";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "更新中";
        ClassicsHeader.REFRESH_HEADER_LASTTIME = "最終更新 M-d HH:mm";
        ClassicsHeader.REFRESH_HEADER_LOADING = "loading中...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "指を離すと更新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "";

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.qiita, android.R.color.white);//全局设置主题颜色
                ClassicsHeader header = new ClassicsHeader(context);
                header.setFinishDuration(0); //设置刷新完成显示的停留时间（设为0可以关闭停留功能）
                header.setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
                return header;
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }
}
