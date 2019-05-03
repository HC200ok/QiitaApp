package com.example.firstapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.firstapplication.bean.Post;
import com.example.firstapplication.bean.Posts;
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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search, null);
            initRecyclerView(view);
            initSearchView(view);
        }
        return view;
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
