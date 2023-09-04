package com.moonmoonbird.mmbuy.model;

public class Base {
    private Integer status;
    private String message;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
