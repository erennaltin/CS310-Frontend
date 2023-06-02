package com.example.test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ForumTopicRepository {

    private static final String basePath = "http://10.51.52.171:8080/Forum/";
    public void getTopic(ExecutorService srv, Handler handler)
    {
        srv.submit(() -> {
                    try {

                        URL url = new URL(basePath +"GetForumTopics");

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String line = "";
                        StringBuilder buffer = new StringBuilder();

                        while ((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }

                        JSONObject responseJSON = new JSONObject(buffer.toString());

                        JSONArray dataArrJSON = new JSONArray(responseJSON.getString("data"));
                        List<ForumTopics> forumTopics = new ArrayList<>();
                        for (int i = 0; i < dataArrJSON.length(); i++)
                        {
                            JSONObject obj = dataArrJSON.getJSONObject(i);
                            ForumTopics forumTopic = new ForumTopics();
                            forumTopic.name = obj.getString("title");

                            forumTopics.add(forumTopic);
                        }
                        connection.disconnect();

                        Message msg = new Message();
                        msg.obj = forumTopics;
                        handler.sendMessage(msg);


                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }

                }
        );
    }
}
