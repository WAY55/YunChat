package com.example.yunchat.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yunchat.R;
import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.models.User;
import com.example.yunchat.task.ImageTask;
import com.example.yunchat.utils.HttpUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 搜索好友列表适配器
 *
 * @author 曾健育
 */
public class UserAdapter extends ArrayAdapter {
    private final int resourceId;

    public UserAdapter(@NonNull Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = (User) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView userImage = (ImageView) view.findViewById(R.id.user_image);
        TextView userName = (TextView) view.findViewById(R.id.user_name);
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
