package com.example.yunchat;

import android.app.Application;
import android.os.Handler;

import com.example.yunchat.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Application类
 * @author 曾健育
 */


public class App extends Application {
    private Handler friendHandle = null;
    public Handler getHandler() {
        return friendHandle;
    }

    public void setHandler(Handler handler) {
        this.friendHandle = handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
