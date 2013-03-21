package com.vteam.xml_project.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class BaseDTO implements Serializable {

    private String status;
    private String msg;

    public BaseDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String statusCode) {
        this.status = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
