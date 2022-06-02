package com.example.embeddedprogrammingassignment.modal;

public class Announcements {

    String title, caption, url, urlToImage, id, numberOfClicks;

    public void setNumberOfClicks(String numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }

    public String getTitle() {
        return title;
    }

    public String getCaption() {
        return caption;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getNumberOfClicks() { return numberOfClicks; }

    public String getId() { return id; }
}
