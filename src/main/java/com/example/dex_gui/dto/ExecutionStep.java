package com.example.dex_gui.dto;

public class ExecutionStep {

    private int step;
    private String title;
    private String description;
    private String status;

    public ExecutionStep() {
    }

    public ExecutionStep(
            int step,
            String title,
            String description,
            String status) {

        this.step = step;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}