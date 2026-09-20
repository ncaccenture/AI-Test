package com.example.dex_gui.dto;

public class UserExecution {

    private String firstName;
    private String lastName;
    private String email;

    private boolean exists;
    private Long userId;

    private String action;

    private boolean notification;
    private boolean sftpCredentials;

    private boolean willCreate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isSftpCredentials() {
        return sftpCredentials;
    }

    public void setSftpCredentials(boolean sftpCredentials) {
        this.sftpCredentials = sftpCredentials;
    }

    public boolean isWillCreate() {
        return willCreate;
    }

    public void setWillCreate(boolean willCreate) {
        this.willCreate = willCreate;
    }
}