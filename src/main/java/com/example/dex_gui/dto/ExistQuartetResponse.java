package com.example.dex_gui.dto;

public class ExistQuartetResponse {

    private String message;
    private boolean isExist;

    public boolean isExist() {
        return isExist;
    }

    public String getMessage() {
        return message;
    }

    public void setExist(boolean exist) {
        this.isExist = exist;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
