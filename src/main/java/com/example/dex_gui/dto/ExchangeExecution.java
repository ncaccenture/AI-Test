package com.example.dex_gui.dto;

public class ExchangeExecution {

    private boolean exists;
    private boolean willCreate;

    private String sourceEndpoint;
    private String destinationEndpoint;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isWillCreate() {
        return willCreate;
    }

    public void setWillCreate(boolean willCreate) {
        this.willCreate = willCreate;
    }

    public String getSourceEndpoint() {
        return sourceEndpoint;
    }

    public void setSourceEndpoint(String sourceEndpoint) {
        this.sourceEndpoint = sourceEndpoint;
    }

    public String getDestinationEndpoint() {
        return destinationEndpoint;
    }

    public void setDestinationEndpoint(String destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }
}