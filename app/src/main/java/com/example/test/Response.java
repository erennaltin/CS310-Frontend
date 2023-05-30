package com.example.test;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class Response {
    public String returnMessage;
    public int returnCode;
    public LocalDateTime date;


    public Response() {
        // TODO Auto-generated constructor stub
    }


    public Response(String returnMessage, int returnCode) {
        super();
        this.returnMessage = returnMessage;
        this.returnCode = returnCode;
        this.date = LocalDateTime.now();
    }

}
