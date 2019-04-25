package com.example.firstapplication.bean;

import java.util.List;

public class Posts {

    private List<Post> results;

    public List getResults() {
        return results;
    }

    public void setResults(List<Post> results) {
        this.results = results;
    }

    public Posts(List results) {
        this.results = results;
    }
}

