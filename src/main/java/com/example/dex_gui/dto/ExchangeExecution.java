package com.example.dex_gui.dto;

public class ExchangeExecution {

    private boolean exists;
    private boolean willCreate;

    private String sourceEndpoint;
    private String destinationEndpoint;
    private String destinationFileNameSuffix;
    private String inboundPath;
    private String outboundPath;

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

    public String getDestinationFileNameSuffix() {
        return destinationFileNameSuffix;
    }

    public void setDestinationFileNameSuffix(String destinationFileNameSuffix) {
        this.destinationFileNameSuffix = destinationFileNameSuffix;
    }

    public String getInboundPath() {
        return inboundPath;
    }

    public void setInboundPath(String inboundPath) {
        this.inboundPath = inboundPath;
    }

    public String getOutboundPath() {
        return outboundPath;
    }

    public void setOutboundPath(String outboundPath) {
        this.outboundPath = outboundPath;
    }
}