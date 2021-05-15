package com.uqubang.mqttim.http;

import com.uqubang.mqttim.data.model.LoginUser;
import com.uqubang.mqttim.ui.login.LoginFormData;
import com.uqubang.mqttim.ui.login.LoginResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountHttpService {

    @POST("account/login")
    Call<LoginResult> login(@Body LoginFormData loginFormData);

}
