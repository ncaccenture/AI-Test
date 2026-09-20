package com.example.dex_gui.dto;

import java.util.List;

public class AutomationExecutionPlan {

    private boolean canProceed;
    private String summary;

    private QuartetExecution quartet;
    private String partnerType;
    private List<UserExecution> users;
    private ExchangeExecution exchange;

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    private List<ExecutionStep> steps;

    public boolean isCanProceed() {
        return canProceed;
    }

    public void setCanProceed(boolean canProceed) {
        this.canProceed = canProceed;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public QuartetExecution getQuartet() {
        return quartet;
    }

    public void setQuartet(QuartetExecution quartet) {
        this.quartet = quartet;
    }

    public List<UserExecution> getUsers() {
        return users;
    }

    public void setUsers(List<UserExecution> users) {
        this.users = users;
    }

    public ExchangeExecution getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeExecution exchange) {
        this.exchange = exchange;
    }

    public List<ExecutionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ExecutionStep> steps) {
        this.steps = steps;
    }
}