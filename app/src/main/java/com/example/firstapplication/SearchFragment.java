package com.example.firstapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.adapter.PostAdapter;
import com.example.firstapplication.bean.Post;
import com.example.firstapplication.bean.Posts;
import com.example.firstapplication.util.OkHttp3Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private SearchView mSearchView;
    private LinearLayout progressLinearLayout;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NavBar.init(getActivity(), false, "検索", "");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, null);
            initRecyclerView(view);
            initSearchView(view);
//            initClickSpan(view);
        }
        return view;
    }

    private void initClickSpan(View view) {
        TextView t1 =  view.findViewById(R.id.clickSpan);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("好友" + i + "，");
        }

        String likeUsers = sb.substring(0, sb.lastIndexOf("，")).toString();
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        t1.setText(addClickPart(likeUsers), TextView.BufferType.SPANNABLE);
    }

    private SpannableStringBuilder addClickPart(String str) {

        ImageSpan imgspan = new ImageSpan(getActivity(), R.drawable.border_qiita);
        SpannableString spanStr = new SpannableString("gasd.");
        spanStr.setSpan(imgspan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //创建一个SpannableStringBuilder对象，连接多个字符串
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
        ssb.append(str);
        String[] likeUsers = str.split("，");
        if (likeUsers.length > 0) {
            for (int i = 0; i < likeUsers.length; i++) {
                final String name = likeUsers[i];
                final int start = str.indexOf(name) + spanStr.length();
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(getActivity(), name,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        //删除下划线，设置字体颜色为蓝色
                        ds.setColor(Color.BLUE);
                        ds.setUnderlineText(false);
                    }
                },start,start + name.length(),0);
            }
        }
        return ssb.append("等" + likeUsers.length + "个人觉得很赞");
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initSearchView(View view) {
        progressLinearLayout = view.findViewById(R.id.loading);
        mSearchView = view.findViewById(R.id.searview);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressLinearLayout.setVisibility(View.VISIBLE);
                getDate(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getDate(String query) {
        String url = "https://qiita.com/api/v2/items?per_page=20&query=" + query;
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Gson解析
                Gson gson = new Gson();
                String posts_string = "{results:" + response.body().string() + "}";
                Posts posts = gson.fromJson(posts_string, Posts.class);
                final List<Post> results = posts.getResults();

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postAdapter = new PostAdapter(getContext(), results);
                            recyclerView.setAdapter(postAdapter);
                            postAdapter.notifyDataSetChanged();
                            progressLinearLayout.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });
    }
}
