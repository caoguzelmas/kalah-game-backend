package com.caoguzelmas.kalah.model;

public class ResponseCode {

    private boolean isSuccess;
    private String message;

    public ResponseCode(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public ResponseCode() {
        this.isSuccess = true;
        this.message = "";
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
