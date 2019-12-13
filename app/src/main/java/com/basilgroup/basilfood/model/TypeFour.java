package com.basilgroup.basilfood.model;

public class TypeFour {
    private int id;
    private String name;
    String created_at;
    String updated_at;
    String image;

    public TypeFour(int id, String name, String created_at, String updated_at, String image) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image = image;
    }

    public TypeFour() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getImage() {
        return image;
    }
}
