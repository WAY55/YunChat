package com.example.yunchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yunchat.R;
import com.example.yunchat.models.UserMessage;

import java.util.ArrayList;

/**
 * 对话框适配器
 * @author 陈树旭
 */
public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<UserMessage> messages;
    private static final int TYPE_LEFT = 1;
    private static final int TYPE_RIGHT = 2;

    public ArrayList<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<UserMessage> messages) {
        this.messages = messages;
        /*强制刷新*/
        notifyDataSetChanged();
    }

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_LEFT:/*对方发送的信息*/
                /*加载布局文件*/
                View viewLeft = LayoutInflater.from(context).inflate(R.layout.message_left, parent, false);
                holder = new LeftViewHolder(viewLeft);
                break;
            case TYPE_RIGHT:/*自己发送的信息*/
                View viewRight = LayoutInflater.from(context).inflate(R.layout.message_right, parent, false);
                holder = new RightViewHolder(viewRight);
                break;
        }
        return holder;
    }

    /*数据填充*/
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*获取信息发送类型*/
        int messageType = getItemViewType(position);
        switch (messageType){
            case TYPE_LEFT:
                LeftViewHolder leftViewHolder = (LeftViewHolder) holder;
                leftViewHolder.leftMessage.setText(messages.get(position).getMessage());
                leftViewHolder.leftName.setText(messages.get(position).getUser().getUsername());
                leftViewHolder.leftTime.setText(messages.get(position).getTime());
                break;
            case TYPE_RIGHT:
                RightViewHolder rightViewHolder = (RightViewHolder) holder;
                rightViewHolder.rightMessage.setText(messages.get(position).getMessage());
                rightViewHolder.rightName.setText(messages.get(position).getUser().getUsername());
                rightViewHolder.rightTime.setText(messages.get(position).getTime());
                break;
        }
    }

    /*返回数据的数量*/
    @Override
    public int getItemCount() {
        return messages!= null && messages.size() > 0 ? messages.size() : 0;
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder{
        private TextView leftMessage;
        private TextView leftName;
        private TextView leftTime;
        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            /*由于itemView是item的布局文件，我们需要的是里面的textView，
            因此利用itemView.findViewById获取里面的textView实例，
            后面通过onBindViewHolder方法能直接填充数据到每一个textView了*/
            leftMessage = (TextView)itemView.findViewById(R.id.dialog_message_left_tv);
            leftName = (TextView)itemView.findViewById(R.id.dialog_message_left_tv_name);
            leftTime = (TextView)itemView.findViewById(R.id.dialog_message_left_tv_time);
        }
    }

    static class RightViewHolder extends RecyclerView.ViewHolder{
        private TextView rightMessage;
        private TextView rightName;
        private TextView rightTime;
        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            rightMessage = (TextView)itemView.findViewById(R.id.dialog_message_right_tv);
            rightName = (TextView)itemView.findViewById(R.id.dialog_message_right_tv_name);
            rightTime = (TextView)itemView.findViewById(R.id.dialog_message_right_tv_time);
        }
    }
}
