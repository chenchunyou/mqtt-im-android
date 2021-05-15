package com.uqubang.mqttim.http;

import com.uqubang.mqttim.data.Result;
import com.uqubang.mqttim.data.model.LoginUser;
import com.uqubang.mqttim.data.model.User;
import com.uqubang.mqttim.http.domain.AjaxResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserHttpService {

    @POST("user/contactList")
    Call<AjaxResult> getContactList(@Body int userId);

    @POST("user/info")
    Call<AjaxResult> getUserById(@Body int userId);
}
