package com.uqubang.mqttim.ui.chat;

import com.uqubang.mqttim.data.model.User;

import java.io.IOException;

public class UserRepository {

    private static volatile UserRepository instance;

    private UserDataSource dataSource;

    private UserRepository(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserRepository getInstance(UserDataSource dataSource) {
        if (instance == null) {
            instance = new UserRepository(dataSource);
        }
        return instance;
    }

    /*
        获取登录用户信息
     */
    public User getUserById(int userId) throws IOException {
        return dataSource.getUserById(userId);
    }



}
