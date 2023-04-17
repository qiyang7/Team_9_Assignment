package com.example.application;

public class ReadingCornerModel {

    private String topicName, description;
    private String topicImage;

    public ReadingCornerModel(){

    }
    public ReadingCornerModel(String topicName, String description , String topicImage) {
        this.topicName = topicName;
        this.topicImage = topicImage;
        this.description = description;

    }
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public void setTopicImage(String topicImage) {
        this.topicImage = topicImage;
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {
        this.description= description;
    }
}
