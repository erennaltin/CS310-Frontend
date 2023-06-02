package com.example.test;

import java.util.List;

public class ForumTopics {

    public String name;
    public String description;
    List<Comment> comments;

    public ForumTopics(){}

    public ForumTopics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
