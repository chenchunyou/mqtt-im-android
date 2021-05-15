package com.uqubang.mqttim.ui.chat;

import com.google.gson.Gson;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.http.UserHttpService;
import com.uqubang.mqttim.http.domain.AjaxResult;
import com.uqubang.mqttim.util.RetrofitUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDataSource {

    private UserDao userDao;

    public UserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    // 获取登录用户信息
    public User getUserById(int userId) throws IOException {

        User user = userDao.select(userId);
        if (user != null) {
            // 数据库有数据
            return user;
        }

        // 从网络中请求数据
        Retrofit retrofit = RetrofitUtil.getRetrofit();
        UserHttpService userHttpService = retrofit.create(UserHttpService.class);

        Call<AjaxResult> getUserByIdCall = userHttpService.getUserById(userId);

        Response<AjaxResult> response = getUserByIdCall.execute();

        AjaxResult ajaxResult = response.body();

        if (ajaxResult.getCode() == 0) {
            Object data = ajaxResult.getData();
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(data), User.class);

        } else {
            return null;
        }
    }


}
