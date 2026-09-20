package com.example.dex_gui.dto;

public class AutomationUser {

    private String action;
    private String studyOwner;
    private String name;
    private String eisaiId;
    private String phone;
    private String email;
    private boolean notify;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStudyOwner() {
        return studyOwner;
    }

    public void setStudyOwner(String studyOwner) {
        this.studyOwner = studyOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEisaiId() {
        return eisaiId;
    }

    public void setEisaiId(String eisaiId) {
        this.eisaiId = eisaiId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }
}
