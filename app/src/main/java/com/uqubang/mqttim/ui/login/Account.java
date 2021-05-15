package com.uqubang.mqttim.ui.login;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/*
    用户账号实体类
 */
@Entity
public class Account {
    // 账号ID
    @PrimaryKey
    private int id;
    // 用户ID
    private int userId;
    // 账户手机号
    private String phone;
    // 账号密码
    private String password;
    // 账号类型
    private String accountType;
    // 账号状态
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
