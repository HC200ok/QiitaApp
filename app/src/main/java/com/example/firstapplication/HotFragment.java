package com.example.firstapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.bean.Post;
import com.example.firstapplication.bean.Posts;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotFragment extends SmartRefreshFragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private PostAdapter postAdapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, null);

        initRecyclerView(view);
        initSmartRefresh(view);

        getData("firstLoad");
        return view;
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initSmartRefresh(View view) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData("refresh");
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData("loadMore");
            }
        });
    }

    private void getData(final String loadType) {
        if (loadType == "firstLoad") page = 1;
        String url = "https://qiita.com/api/v2/items?per_page=20&page=" + page;
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
                            if (loadType == "firstLoad") {
                                postAdapter = new PostAdapter(getContext(), results);
                                recyclerView.setAdapter(postAdapter);
                                postAdapter.notifyDataSetChanged();
                            } else if (loadType == "refresh") {
                                postAdapter.refreshData(results);
                                refreshLayout.finishRefresh();
                            } else if (loadType == "loadMore") {
                                postAdapter.loadMore(results);
                                refreshLayout.finishLoadmore();
                            }
                            page++;
                        }
                    });
                }

            }
        });
    }
}
