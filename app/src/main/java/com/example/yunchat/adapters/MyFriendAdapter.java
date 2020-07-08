package com.example.yunchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yunchat.R;
import com.example.yunchat.models.User;
import com.example.yunchat.task.ImageTask;

import java.util.List;

/**
 * 好友列表适配器
 * @author 曾健育
 */
public class MyFriendAdapter extends ArrayAdapter {
    private final int resourceId;

    public MyFriendAdapter(@NonNull Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = (User) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        //初始化用户头像
        ImageView userImage = (ImageView) view.findViewById(R.id.friend_image);
        //初始化用户名
        TextView userName = (TextView) view.findViewById(R.id.friend_name);
        //获取头像
        try {
            ImageTask task = new ImageTask(userImage);
            task.execute(user.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        userName.setText(user.getUsername());
        return view;
    }
}
