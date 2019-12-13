package com.basilgroup.basilfood.model;

public class Message {

    private int id;
    private String title;
    private String text_user;
    private String date_user;
    private String code;
    private String token;
    private String text_admin;
    private String date_admin;
    private String created_at;
    private String updated_at;

    public Message(int id, String title, String text_user, String date_user, String code, String token, String text_admin, String date_admin, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.text_user = text_user;
        this.date_user = date_user;
        this.code = code;
        this.token = token;
        this.text_admin = text_admin;
        this.date_admin = date_admin;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText_user() {
        return text_user;
    }

    public String getDate_user() {
        return date_user;
    }

    public String getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public String getText_admin() {
        return text_admin;
    }

    public String getDate_admin() {
        return date_admin;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
