package com.example.dex_gui.dto;

import java.util.List;

public class AutomationExecutionResult {

    private boolean success;
    private String message;
    private List<ExecutionStep> steps;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExecutionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ExecutionStep> steps) {
        this.steps = steps;
    }
}
