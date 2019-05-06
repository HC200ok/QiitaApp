package com.example.firstapplication.bean;

public class Tag {
    private String name;
    private String id;
    private String icon_url;
    private int followers_count;
    private int items_count;

    public String getId() {
        return id;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getItems_count() {
        return items_count;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setIcon_url(String icon_ur) {
        this.icon_url = icon_ur;
    }

    public void setItems_count(int items_count) {
        this.items_count = items_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
