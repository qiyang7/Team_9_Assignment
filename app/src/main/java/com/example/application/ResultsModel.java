package com.example.application;

public class ResultsModel {

    private int score;
    private String quizID;

    public ResultsModel(int score, String quizID) {
        this.score = score;
        this.quizID = quizID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
}
