package com.uqubang.mqttim.ui.login;

public class LoginResult {

    private int code;
    private String msg;
    private Account account;

    public LoginResult(int code) {
        this.code = code;
    }

    public LoginResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public LoginResult(int code, String msg, Account account) {
        this.code = code;
        this.msg = msg;
        this.account = account;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
