package com.example.firstapplication.bean;

import java.util.List;

public class Post {
    private String id;
    private String created_at;
    private String title;
    private String rendered_body;
    private User user;
    private List<Tag> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getRendered_body() {
        return rendered_body;
    }

    public void setRendered_body(String rendered_body) {
        this.rendered_body = rendered_body;
    }
}
