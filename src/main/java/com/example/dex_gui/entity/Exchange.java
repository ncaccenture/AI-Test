package com.example.dex_gui.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "exchanges")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    // ================= SOURCE =================

    @Column(nullable = false)
    private String sourceExchangeType;

    private String sourceTrigger;
    private Integer sourcePollingFrequency;
    private String sourceExchangeNameSuffix;
    private String sourceEndpoint;
    private String sourceInboundPath;
    private String sourceOutboundPath;
    private String sourceFileNameSuffix;
    private boolean sourceEnabled = true;
    private boolean sourceExtractZip;


    @ManyToOne
    @JoinColumn(name = "source_quartet_id", nullable = false)
    private Quartet sourceQuartet;


    // ================= DESTINATION =================

    @Column(nullable = false)
    private String destinationExchangeType;

    private String destinationTrigger;
    private Integer destinationPollingFrequency;
    private String destinationEndpoint;
    private String destinationInboundPath;
    private String destinationOutboundPath;
    private String destinationFileNameSuffix;
    private boolean destinationEnabled = true;
    private boolean destinationExtractZip;


    @ManyToOne
    @JoinColumn(name = "destination_quartet_id", nullable = false)
    private Quartet destinationQuartet;


    // ================= OTHER =================

    private boolean bidirectional;


    // ================= NOTIFICATION USERS =================

    @ManyToMany
    @JoinTable(
            name = "exchange_notification_users",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> notificationUsers = new HashSet<>();


    // ================= CONSTRUCTORS =================

//    protected Exchange() {
//    }


//    public Exchange(
//            String sourceExchangeType,
//            String destinationExchangeType) {
//
//        this.sourceExchangeType = sourceExchangeType;
//        this.destinationExchangeType = destinationExchangeType;
//    }


    // ================= GETTERS / SETTERS =================

    public UUID getId() {
        return id;
    }


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


    public Quartet getSourceQuartet() {
        return sourceQuartet;
    }

    public void setSourceQuartet(Quartet sourceQuartet) {
        this.sourceQuartet = sourceQuartet;
    }


    // ================= DESTINATION =================

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


    public Quartet getDestinationQuartet() {
        return destinationQuartet;
    }

    public void setDestinationQuartet(Quartet destinationQuartet) {
        this.destinationQuartet = destinationQuartet;
    }


    // ================= OTHER =================

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setBidirectional(boolean bidirectional) {
        this.bidirectional = bidirectional;
    }


    // ================= USERS =================

    public Set<User> getNotificationUsers() {
        return notificationUsers;
    }

    public void setNotificationUsers(Set<User> notificationUsers) {
        this.notificationUsers = notificationUsers;
    }


    public void addNotificationUser(User user) {
        notificationUsers.add(user);
    }

    public void removeNotificationUser(User user) {
        notificationUsers.remove(user);
    }
}