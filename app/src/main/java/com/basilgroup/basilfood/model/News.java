package com.basilgroup.basilfood.model;

public class News {

    private int id;
    private String title;
    private String text;
    private String image;
    private String date;
    private String created_at;
    private String updated_at;

    public News(int id, String title, String text, String image, String date, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public News() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
