package com.example.test;

public class Comment {
    String username;
    String dateCreated;
    String text;

    public Comment() {}

    public Comment(String username, String dateCreated, String text) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.text = text;
    }
}
