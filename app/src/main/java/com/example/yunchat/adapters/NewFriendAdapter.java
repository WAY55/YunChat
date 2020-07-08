package com.example.yunchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yunchat.R;
import com.example.yunchat.models.AddFriendMessage;
import com.example.yunchat.models.User;
import com.example.yunchat.task.AcceptTask;
import com.example.yunchat.task.ImageTask;
import com.example.yunchat.task.UsernameTask;

import org.w3c.dom.Text;

import java.util.List;

public class NewFriendAdapter extends ArrayAdapter {
    private final int resourceId;

    public NewFriendAdapter(@NonNull Context context, int resource, List<AddFriendMessage> messages) {
        super(context, resource, messages);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AddFriendMessage message = (AddFriendMessage) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView avatar = view.findViewById(R.id.new_friends_avatar);
        TextView username = view.findViewById(R.id.new_friends_username);
        TextView msg = view.findViewById(R.id.new_friends_message);

        try {
            //获取头像
            ImageTask task = new ImageTask(avatar);
//            获取用户名
            UsernameTask task1 = new UsernameTask(username);
            task.execute("openId/" + message.getSendUser());
            task1.execute(message.getSendUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (message.getMessage() != null || !message.getMessage().isEmpty()) {
            msg.setText(message.getMessage());
        }

        int accept = message.getAccept();
        if (accept == 1) {
            view.findViewById(R.id.accept_text).setVisibility(View.VISIBLE);
        }else {
            Button button = view.findViewById(R.id.accept_button);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v->{
                try {
                    AcceptTask task = new AcceptTask(view);
                    task.execute(message.getId());
                } catch (IllegalAccessError error) {
                    error.printStackTrace();
                }
            });
        }
        return view;
    }
}
