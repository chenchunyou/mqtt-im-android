package com.uqubang.mqttim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// 聊天消息实体类
@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String phone;
    // 内容
    private String content;

    private String headImgUrl;

    // 状态
    private int status;

    // 接收还是发送
    boolean send;

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
