package com.example.application;

import android.widget.ProgressBar;

public class TestModel {


    private String topicQuiz;
    private int scoreQuiz;


    public TestModel() {

    }

    public TestModel( String topicQuiz, int scoreQuiz) {
        this.topicQuiz = topicQuiz;
        this.scoreQuiz = scoreQuiz;


    }

    public void setScoreQuiz(int scoreQuiz) {
        this.scoreQuiz = scoreQuiz;
    }

    public String getTopicQuiz() {
        return topicQuiz;
    }

    public void setTopicQuiz(String topicQuiz) {
        this.topicQuiz = topicQuiz;
    }

    public int getScoreQuiz() {
        return scoreQuiz;
    }

}