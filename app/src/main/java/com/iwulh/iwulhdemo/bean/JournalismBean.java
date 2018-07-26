/**
 * Copyright 2018 aTool.org
 */
package com.iwulh.iwulhdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-07-12 17:14:51
 *
 * @author aTool.org (i@aTool.org)
 * @website http://www.atool.org/json2javabean.php
 */
public class JournalismBean implements Serializable {

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