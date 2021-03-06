package com.uqubang.mqttim.http.domain;

public class AjaxResult {
    public AjaxResult() {
    }

    public AjaxResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AjaxResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
