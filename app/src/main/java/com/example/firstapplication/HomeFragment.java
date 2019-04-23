package com.example.firstapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        tabLayout = getView().findViewById(R.id.tablayout);
        viewPager = getView().findViewById(R.id.viewpager);

        fragments.add(new HotFragment());
        fragments.add(new FollowFragment());

        titles.add("选项卡1");
        titles.add("选项卡2");

        tabLayout.addTab(tabLayout.newTab().setText("选项卡1"));
        tabLayout.addTab(tabLayout.newTab().setText("选项卡2"));

        MyAdapter adapter = new MyAdapter(getFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }


}
