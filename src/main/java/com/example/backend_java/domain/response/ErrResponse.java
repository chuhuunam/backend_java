package com.example.backend_java.domain.response;

import java.io.Serializable;

public class ErrResponse<T> implements Serializable {
    private static final long serialVersionUID = -5997809745775637818L;

    private int status;
    private String msg;
    private T errMessage;

    public ErrResponse() {
    }

    public ErrResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ErrResponse(int status, String msg, T errMessage) {
        this.status = status;
        this.msg = msg;
        this.errMessage = errMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(T errMessage) {
        this.errMessage = errMessage;
    }
}

