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
import com.example.firstapplication.bean.User;
import com.example.firstapplication.helper.UserHelper;

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
            initView();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        if (UserHelper.getInstance().isLogin()) {
            hideEmpty();
            initUserInfo();
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

    private void initUserInfo() {
        ImageView IvUserImg = view.findViewById(R.id.user_img);
        TextView TvUserId = view.findViewById(R.id.user_id);
        TextView TvUserName = view.findViewById(R.id.user_name);

        User user = UserHelper.getInstance().getUser();
        Glide.with(getContext())
                .load(user.getProfile_image_url())
                .into(IvUserImg);
        TvUserId.setText(user.getId());
        TvUserName.setText(user.getName());
        userInfoLinearLayout.setVisibility(View.VISIBLE);
    }
}
