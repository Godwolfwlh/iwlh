package com.iwulh.iwulhdemo.bean;

import java.io.Serializable;
import java.util.List;

public class JournalismNBean implements Serializable {

    private int code;
    private String msg;
    private int count;
    private List<Journalism> data;

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

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setData(List<Journalism> data) {
        this.data = data;
    }

    public List<Journalism> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "JournalismBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}