package com.example.firstapplication.bean;

import java.util.List;

public class Tags {
    private List<Tag> results;

    public List getResults() {
        return results;
    }

    public void setResults(List<Tag> results) {
        this.results = results;
    }

    public Tags(List results) {
        this.results = results;
    }
}
