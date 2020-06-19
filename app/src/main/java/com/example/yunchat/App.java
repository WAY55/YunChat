package com.example.yunchat;

import android.app.Application;

import com.example.yunchat.models.User;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Application类
 * @author 曾健育
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class App extends Application {
    private User user;
}
