package com.example.dex_gui.dto;

public class ExchangeSearchRequest {

    private String compound;
    private String study;
    private String department;
    private String partner;
    private String sourceEndpoint;
    private String destinationEndpoint;

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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