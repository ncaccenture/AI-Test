package com.example.dex_gui.dto;
import java.util.List;

public class AutomationFormData {
    private String partnerAction;

    private String businessUnit;
    private String department;
    private String compound;
    private String study;

    private String partnerType;
    private String partnerCompanyName;
    private String partnerIpRange;
    private String inboundPath;
    private String outboundPath;

    private List<AutomationUser> users;
    private List<PartnerContact> partnerContacts;

    public String getPartnerAction() {
        return partnerAction;
    }

    public void setPartnerAction(String partnerAction) {
        this.partnerAction = partnerAction;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getPartnerCompanyName() {
        return partnerCompanyName;
    }

    public void setPartnerCompanyName(String partnerCompanyName) {
        this.partnerCompanyName = partnerCompanyName;
    }

    public String getPartnerIpRange() {
        return partnerIpRange;
    }

    public void setPartnerIpRange(String partnerIpRange) {
        this.partnerIpRange = partnerIpRange;
    }

    public List<AutomationUser> getUsers() {
        return users;
    }

    public void setUsers(List<AutomationUser> users) {
        this.users = users;
    }

    public List<PartnerContact> getPartnerContacts() {
        return partnerContacts;
    }

    public void setPartnerContacts(List<PartnerContact> partnerContacts) {
        this.partnerContacts = partnerContacts;
    }

    public void setInboundPath(String inboundPath) {
        this.inboundPath = inboundPath;
    }

    public String getInboundPath() {
        return inboundPath;
    }

    public void setOutboundPath(String outboundPath) {
        this.outboundPath = outboundPath;
    }

    public String getOutboundPath() {
        return outboundPath;
    }

}
