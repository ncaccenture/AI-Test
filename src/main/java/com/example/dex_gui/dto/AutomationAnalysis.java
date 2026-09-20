package com.example.dex_gui.dto;

import java.util.List;

public class AutomationAnalysis {

    private boolean canProceed;

    private String summary;

    private QuartetAnalysis quartet;

    private List<UserAnalysis> users;

    private ExchangeAnalysis exchange;

    private List<String> steps;

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

    public QuartetAnalysis getQuartet() {
        return quartet;
    }

    public void setQuartet(QuartetAnalysis quartet) {
        this.quartet = quartet;
    }

    public List<UserAnalysis> getUsers() {
        return users;
    }

    public void setUsers(List<UserAnalysis> users) {
        this.users = users;
    }

    public ExchangeAnalysis getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeAnalysis exchange) {
        this.exchange = exchange;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
// getters and setters
}
