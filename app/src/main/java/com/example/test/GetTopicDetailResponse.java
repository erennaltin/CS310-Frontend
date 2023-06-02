package com.example.test;

public class GetTopicDetailResponse extends Response {
    ForumTopics data;

    GetTopicDetailResponse() {}

    public GetTopicDetailResponse(ForumTopics data) {
        this.data = data;
    }

    public GetTopicDetailResponse(String returnMessage, int returnCode, ForumTopics data) {
        super(returnMessage, returnCode);
        this.data = data;
    }
}
