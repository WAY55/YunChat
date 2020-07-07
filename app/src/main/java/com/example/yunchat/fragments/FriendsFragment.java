package com.example.yunchat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.activities.NewFriendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用于显示通讯录
 *
 * @author 曾健育
 */
public class FriendsFragment extends Fragment {
    /**
     * ButterKnife
     */
    private Unbinder unbinder;
    @BindView(R.id.new_friends_click)
    LinearLayout newFriends;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        initNewFriendClick();
    }

    private void initNewFriendClick() {
        newFriends.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewFriendActivity.class);
            startActivityForResult(intent, 3);
            getActivity().overridePendingTransition(R.anim.open_enter_t, R.anim.open_exit_t);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
