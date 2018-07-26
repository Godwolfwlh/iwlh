package com.iwulh.iwulhdemo.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {
    private int code;
    private String msg;
    private int state;
    private Data data;

    public LoginBean() {}

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", state=" + state +
                ", data=" + data +
                '}';
    }

}
