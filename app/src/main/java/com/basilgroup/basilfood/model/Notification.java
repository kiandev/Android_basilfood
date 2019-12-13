package com.basilgroup.basilfood.model;

public class Notification {

    private int id;
    private String title;
    private String text;
    private String date;
    private String created_at;
    private String updated_at;

    public Notification(int id, String title, String text, String date, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Notification() {
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
