package com.example.task_5;

public class Food {
    String name;
    int thumbnail;
    Boolean promotion;

    public Food(String name, int thumbnail, Boolean promotion) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public Boolean getPromotion() {
        return promotion;
    }
}
