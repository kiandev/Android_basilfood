package com.basilgroup.basilfood.model;

public class Food {

    private int id;
    private String name;
    private String text;
    private String image;
    private int price;
    private String type_one;
    private String type_two;
    private String type_three;
    private String type_four;
    private String created_at;
    private String updated_at;

    public Food(int id, String name, String text, String image, int price, String type_one, String type_two, String type_three, String type_four, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.image = image;
        this.price = price;
        this.type_one = type_one;
        this.type_two = type_two;
        this.type_three = type_three;
        this.type_four = type_four;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Food() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public String getType_one() {
        return type_one;
    }

    public String getType_two() {
        return type_two;
    }

    public String getType_three() {
        return type_three;
    }

    public String getType_four() {
        return type_four;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
