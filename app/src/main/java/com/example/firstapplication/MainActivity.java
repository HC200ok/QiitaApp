package com.example.firstapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.firstapplication.bean.Tab;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs= new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    private void initTab() {
        Tab Tab_home = new Tab(R.drawable.selector_home, R.string.home, HomeFragment.class);
        Tab Tab_search = new Tab(R.drawable.selector_search, R.string.search, SearchFragment.class);
        Tab Tab_user = new Tab(R.drawable.selector_user, R.string.user, UserFragment.class);

        mTabs.add(Tab_home);
        mTabs.add(Tab_search);
        mTabs.add(Tab_user);

        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(tab.getText()));
            tabSpec.setIndicator(buildView(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    private View buildView(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_item, null);
        ImageView Tab_img = view.findViewById(R.id.tab_img);
        TextView Tab_txt = view.findViewById(R.id.tab_txt);

        Tab_img.setBackgroundResource(tab.getImage());
        Tab_txt.setText(tab.getText());

        return view;
    }
}

