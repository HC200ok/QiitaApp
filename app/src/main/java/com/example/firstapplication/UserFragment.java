package com.example.firstapplication;


import android.net.Uri;
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
import com.example.firstapplication.bean.Post;
import com.example.firstapplication.bean.Posts;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.SPUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user, container, false);
            initView(view);
        }
        return view;
    }

    private void initView(View view) {
        emptyLinearLayout = view.findViewById(R.id.empty);
        userInfoLinearLayout = view.findViewById(R.id.user_info);
        String token = UserHelper.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            hideUserInfo();
            showEmpty(view);
        } else {
            hideEmpty();
            initUserInfo(view);
        }
    }

    private void showEmpty(View view) {
        emptyLinearLayout.setVisibility(View.VISIBLE);
    }

    private void hideUserInfo() {
        userInfoLinearLayout.setVisibility(View.GONE);
    }

    private void hideEmpty() {
        emptyLinearLayout.setVisibility(View.GONE);
    }

    private void initUserInfo(final View view) {
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
