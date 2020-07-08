package com.example.yunchat.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.activities.MessageServerActivity;
import com.example.yunchat.activities.SearchFriendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 用于显示消息页
 *
 * @author 曾健育
 */
public class MessagesFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "MessagesFragment";
    @BindView(R.id.ChatRoom)
    Button button;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages_fragment, container, false);
        AppCompatActivity activity = (HomeActivity) getActivity();

        //获取ToolBar;
        Toolbar toolbar = view.findViewById(R.id.message_toolbar);
        //ToolBar菜单
        toolbar.inflateMenu(R.menu.messages_more_menu);
        //添加菜单事件
        toolbar.setOnMenuItemClickListener(this);
        unbinder = ButterKnife.bind(this, view);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MessageServerActivity.class);
        });
        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_friend:
                //转至搜索好友界面
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                startActivityForResult(intent, 2);
                break;
            default:
        }
        return false;
    }
}
