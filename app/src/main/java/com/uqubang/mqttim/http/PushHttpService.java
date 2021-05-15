package com.uqubang.mqttim.http;

import com.uqubang.mqttim.data.model.LoginUser;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.http.domain.AjaxResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PushHttpService {

    @POST("push")
    Call<AjaxResult> pushMessage(@Body PushMessage pushMessage);

}
