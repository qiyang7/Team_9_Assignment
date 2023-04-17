package com.example.application;

public class ReportModel {

    private String topicReport;
    private int progress;

    public ReportModel(String topicReport, int progress) {
        this.topicReport = topicReport;
        this.progress = progress;
    }

    public String getTopicReport() {
        return topicReport;
    }

    public void setTopicReport(String topicReport) {
        this.topicReport = topicReport;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
