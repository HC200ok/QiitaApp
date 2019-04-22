package com.example.firstapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.firstapplication.bean.Tab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    //    定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    private LayoutInflater mInflater;

    private List<Tab> mTabs = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    private void initTab() {
        Tab tab_home = new Tab(HomeFragment.class, 0);
        Tab tab_search = new Tab(SearchFragment.class, 1);

        mTabs.add(tab_home);
        mTabs.add(tab_search);

        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }

    }

    private View buildIndicator(Tab tab) {
        View view = mInflater.inflate(R.layout.tab_item, null);
        TextView text = view.findViewById(R.id.textview);
        text.setText(tab.getTitle());
        return view;
    }
}

