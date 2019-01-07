package com.example.tkw.lrucachetest.News;

public class Stories {

    private int id;

    private String images;

    private String title;

    private int date;

    private String stories_address;

    public String getStories_address() {
        return stories_address;
    }

    public void setStories_address(String stories_address) {
        this.stories_address = stories_address;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
