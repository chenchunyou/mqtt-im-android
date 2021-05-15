package com.uqubang.mqttim.data;

import com.uqubang.mqttim.data.model.ChatMessage;
import com.uqubang.mqttim.data.model.PushMessage;

import java.util.List;

public class ChatRepository {

    private static volatile ChatRepository instance;

    private ChatDataSource dataSource;

    private ChatRepository(ChatDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ChatRepository getInstance(ChatDataSource dataSource) {
        if (instance == null) {
            instance = new ChatRepository(dataSource);
        }
        return instance;
    }

    public List<ChatMessage> getChatMessageList(String phone) {
        return dataSource.getChatMessageList(phone);
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        dataSource.saveChatMessage(chatMessage);
    }

    public boolean pushMessage(PushMessage pushMessage) {
        return dataSource.pushMessage(pushMessage);
    }

    public void updateChatMessage(ChatMessage chatMessage) {
        dataSource.updateChatMessage(chatMessage);
    }

}
