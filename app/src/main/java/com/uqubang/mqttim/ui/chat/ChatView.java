package com.uqubang.mqttim.ui.chat;

import com.uqubang.mqttim.data.model.ChatMessage;

import java.util.List;

// 聊天视图所需数据实体类
public class ChatView {

    // 对方昵称
    private String nickName;

    //聊天记录集合
    private List<ChatMessage> chatMessageList;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }
}
