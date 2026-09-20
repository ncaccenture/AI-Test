package com.example.dex_gui.dto;

public class PartnerContact {

    private String action;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean notification;
    private boolean sftpCredentials;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isNotification() {
        return notification;
    }

    public boolean isSftpCredentials() {
        return sftpCredentials;
    }

    public void setSftpCredentials(boolean sftpCredentials) {
        this.sftpCredentials = sftpCredentials;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

}
