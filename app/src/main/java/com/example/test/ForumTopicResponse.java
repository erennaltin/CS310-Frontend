package com.example.test;

import java.util.List;

public class ForumTopicResponse extends Response {

    List<ForumTopics> forumTopics;

    ForumTopicResponse(){}

    public ForumTopicResponse(List<ForumTopics> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public ForumTopicResponse(String returnMessage, int returnCode, List<ForumTopics> forumTopics) {
        super(returnMessage,returnCode);
        this.forumTopics = forumTopics;
    }

}
