package com.example.dex_gui.dto;

import java.util.List;

public class ExchangeRequest {

    private String sourceExchangeType;
    private String sourceTrigger;
    private Integer sourcePollingFrequency;
    private String sourceExchangeNameSuffix;
    private String sourceEndpoint;
    private String sourceInboundPath;
    private String sourceOutboundPath;
    private String sourceFileNameSuffix;
    private boolean sourceEnabled;
    private boolean sourceExtractZip;

    private String destinationExchangeType;
    private String destinationTrigger;
    private Integer destinationPollingFrequency;
    private String destinationEndpoint;
    private String destinationInboundPath;
    private String destinationOutboundPath;
    private String destinationFileNameSuffix;
    private boolean destinationEnabled;
    private boolean destinationExtractZip;

    private String compound;
    private String study;
    private String department;
    private String partner;

    private boolean bidirectional;

    private List<String> notifyEmails;


    public String getSourceExchangeType() {
        return sourceExchangeType;
    }

    public void setSourceExchangeType(String sourceExchangeType) {
        this.sourceExchangeType = sourceExchangeType;
    }

    public String getSourceTrigger() {
        return sourceTrigger;
    }

    public void setSourceTrigger(String sourceTrigger) {
        this.sourceTrigger = sourceTrigger;
    }

    public Integer getSourcePollingFrequency() {
        return sourcePollingFrequency;
    }

    public void setSourcePollingFrequency(Integer sourcePollingFrequency) {
        this.sourcePollingFrequency = sourcePollingFrequency;
    }

    public String getSourceExchangeNameSuffix() {
        return sourceExchangeNameSuffix;
    }

    public void setSourceExchangeNameSuffix(String sourceExchangeNameSuffix) {
        this.sourceExchangeNameSuffix = sourceExchangeNameSuffix;
    }

    public String getSourceEndpoint() {
        return sourceEndpoint;
    }

    public void setSourceEndpoint(String sourceEndpoint) {
        this.sourceEndpoint = sourceEndpoint;
    }

    public String getSourceInboundPath() {
        return sourceInboundPath;
    }

    public void setSourceInboundPath(String sourceInboundPath) {
        this.sourceInboundPath = sourceInboundPath;
    }

    public String getSourceOutboundPath() {
        return sourceOutboundPath;
    }

    public void setSourceOutboundPath(String sourceOutboundPath) {
        this.sourceOutboundPath = sourceOutboundPath;
    }

    public String getSourceFileNameSuffix() {
        return sourceFileNameSuffix;
    }

    public void setSourceFileNameSuffix(String sourceFileNameSuffix) {
        this.sourceFileNameSuffix = sourceFileNameSuffix;
    }

    public boolean isSourceEnabled() {
        return sourceEnabled;
    }

    public void setSourceEnabled(boolean sourceEnabled) {
        this.sourceEnabled = sourceEnabled;
    }

    public boolean isSourceExtractZip() {
        return sourceExtractZip;
    }

    public void setSourceExtractZip(boolean sourceExtractZip) {
        this.sourceExtractZip = sourceExtractZip;
    }


    public String getDestinationExchangeType() {
        return destinationExchangeType;
    }

    public void setDestinationExchangeType(String destinationExchangeType) {
        this.destinationExchangeType = destinationExchangeType;
    }

    public String getDestinationTrigger() {
        return destinationTrigger;
    }

    public void setDestinationTrigger(String destinationTrigger) {
        this.destinationTrigger = destinationTrigger;
    }

    public Integer getDestinationPollingFrequency() {
        return destinationPollingFrequency;
    }

    public void setDestinationPollingFrequency(Integer destinationPollingFrequency) {
        this.destinationPollingFrequency = destinationPollingFrequency;
    }

    public String getDestinationEndpoint() {
        return destinationEndpoint;
    }

    public void setDestinationEndpoint(String destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }

    public String getDestinationInboundPath() {
        return destinationInboundPath;
    }

    public void setDestinationInboundPath(String destinationInboundPath) {
        this.destinationInboundPath = destinationInboundPath;
    }

    public String getDestinationOutboundPath() {
        return destinationOutboundPath;
    }

    public void setDestinationOutboundPath(String destinationOutboundPath) {
        this.destinationOutboundPath = destinationOutboundPath;
    }

    public String getDestinationFileNameSuffix() {
        return destinationFileNameSuffix;
    }

    public void setDestinationFileNameSuffix(String destinationFileNameSuffix) {
        this.destinationFileNameSuffix = destinationFileNameSuffix;
    }

    public boolean isDestinationEnabled() {
        return destinationEnabled;
    }

    public void setDestinationEnabled(boolean destinationEnabled) {
        this.destinationEnabled = destinationEnabled;
    }

    public boolean isDestinationExtractZip() {
        return destinationExtractZip;
    }

    public void setDestinationExtractZip(boolean destinationExtractZip) {
        this.destinationExtractZip = destinationExtractZip;
    }


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


    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }

    public List<String> getNotifyEmails() {
        return notifyEmails;
    }

    public void setNotifyEmails(List<String> notifyEmails) {
        this.notifyEmails = notifyEmails;
    }
}