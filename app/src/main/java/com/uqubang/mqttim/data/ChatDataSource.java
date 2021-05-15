package com.uqubang.mqttim.data;

import com.uqubang.mqttim.data.model.ChatMessage;
import com.uqubang.mqttim.data.model.PushMessage;
import com.uqubang.mqttim.http.PushHttpService;
import com.uqubang.mqttim.http.UserHttpService;
import com.uqubang.mqttim.http.domain.AjaxResult;
import com.uqubang.mqttim.util.RetrofitUtil;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatDataSource {

    private ChatDao chatDao;
    //private final String baseUrl = "http://192.168.1.109:8080/";

    public ChatDataSource(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    /**
     * 获取聊天记录集合
     * @param phone
     * @return
     */
    public List<ChatMessage> getChatMessageList(String phone) {
        return chatDao.select(phone);
    }

    /**
     * 保存聊天记录
     * @param chatMessage
     */
    public void saveChatMessage(ChatMessage chatMessage) {
        chatDao.insert(chatMessage);
    }

    public boolean pushMessage(PushMessage pushMessage) {
        Retrofit retrofit = RetrofitUtil.getRetrofit();

        PushHttpService service = retrofit.create(PushHttpService.class);

        Call<AjaxResult> pushMessageCall = service.pushMessage(pushMessage);

        Response<AjaxResult> response;
        try {
            response = pushMessageCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        assert response.body() != null;
        return response.body().getCode() == 0;

    }

    public void updateChatMessage(ChatMessage chatMessage) {
        chatDao.update(chatMessage);
    }


}
