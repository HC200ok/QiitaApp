package com.example.firstapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.firstapplication.adapter.TagAdapter;
import com.example.firstapplication.bean.Tag;
import com.example.firstapplication.bean.Tags;
import com.example.firstapplication.constants.QiitaConstants;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.OkHttp3Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FollowFragment extends Fragment {
    LinearLayout contentLayout;
    LinearLayout loginLayout;
    LinearLayout loadingLayout;
    LinearLayout emptyLayout;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, null);
        contentLayout = view.findViewById(R.id.content_layout);
        loginLayout = view.findViewById(R.id.login_layout);
        loadingLayout = view.findViewById(R.id.loading_layout);
        recyclerView = view.findViewById(R.id.recyclerview);
        emptyLayout = view.findViewById(R.id.empty_layout);
        initRecyclerView();
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initView() {
        if (UserHelper.getInstance().isLogin()) {
            contentLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            getTags();
        } else {
            contentLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getTags() {
        loadingLayout.setVisibility(View.VISIBLE);
        String user_id = UserHelper.getInstance().getUser().getId();
        String url = QiitaConstants.HOST + "/users/" + user_id + "/following_tags";
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String res = "{results:" + response.body().string() + "}";
                    Tags tags = gson.fromJson(res, Tags.class);
                    final List<Tag> results = tags.getResults();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingLayout.setVisibility(View.GONE);
                            TagAdapter tagAdapter = new TagAdapter(getContext(), results);
                            if (tagAdapter.getItemCount() == 0) {
                                emptyLayout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                recyclerView.setAdapter(tagAdapter);
                                tagAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }

}
