package com.example.firstapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.OkHttp3Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserFragment extends Fragment {
    LinearLayout emptyLinearLayout;
    LinearLayout userInfoLinearLayout;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NavBar.init(getActivity(), false, "プロフィール", "setting");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user, container, false);
            emptyLinearLayout = view.findViewById(R.id.empty);
            userInfoLinearLayout = view.findViewById(R.id.user_info);
            initView(view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView(view);
    }

    private void initView(View view) {
        if (UserHelper.getInstance().isLogin()) {
            hideEmpty();
            initUserInfo(view);
        } else {
            hideUserInfo();
            showEmpty();
        }
    }

    private void showEmpty() {
        emptyLinearLayout.setVisibility(View.VISIBLE);
    }

    private void hideUserInfo() {
        userInfoLinearLayout.setVisibility(View.GONE);
    }

    private void hideEmpty() {
        emptyLinearLayout.setVisibility(View.GONE);
    }

    private void initUserInfo(final View view) {
        userInfoLinearLayout.setVisibility(View.VISIBLE);
        String url = "https://qiita.com/api/v2/authenticated_user";
        final ImageView IvUserImg = view.findViewById(R.id.user_img);
        final TextView TvUserId = view.findViewById(R.id.user_id);
        final TextView TvUserName = view.findViewById(R.id.user_name);
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        final JSONObject jsonObject = new JSONObject(response.body().string());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Glide.with(getContext())
                                            .load(jsonObject.getString("profile_image_url"))
                                            .into(IvUserImg);
                                    TvUserId.setText(jsonObject.getString("id"));
                                    TvUserName.setText(jsonObject.getString("name"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
