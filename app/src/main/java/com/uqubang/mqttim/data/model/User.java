package com.uqubang.mqttim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/*
 * 用户信息实体类
 */
@Entity
public class User {
    // 用户ID
    @PrimaryKey
    private int id;
    // 用户名
    private String name;
    // 用户头像
    private String avatar;
    // 用户手机号
    private String phone;
    // 用户邮箱
    private String email;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
