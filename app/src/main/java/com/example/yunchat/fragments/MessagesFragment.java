package com.example.yunchat.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.yunchat.R;
import com.example.yunchat.activities.HomeActivity;
import com.example.yunchat.dialogs.DialogMessage;

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
    @BindView(R.id.shabichenshuxu)
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
            DialogMessage dialog = new DialogMessage(getContext(),activity);
            dialog.show();
            Window window = dialog.getWindow();
            //通过window去掉对话框的默认背景
            assert window != null;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                Toast.makeText(getActivity(), "添加朋友", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return false;
    }
}
