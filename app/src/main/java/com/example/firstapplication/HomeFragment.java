package com.example.firstapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstapplication.adapter.MyAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private View view;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NavBar.init(getActivity(),false, "Qiita", "");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);
            initView(view);
        }
        return view;
    }


    private void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);

        fragments.add(new HotFragment());
        fragments.add(new FollowFragment());

        titles.add("新着");
        titles.add("フォロー中");

        tabLayout.addTab(tabLayout.newTab().setText("新着"));
        tabLayout.addTab(tabLayout.newTab().setText("フォロー中"));

        MyAdapter adapter = new MyAdapter(getActivity().getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
