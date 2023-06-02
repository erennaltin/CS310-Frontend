package com.example.test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    public void getTopicDetail(ExecutorService srv, Handler handler, String topicName)
    {
        srv.submit(() -> {
                    try {

                        URL url = new URL(basePath +"GetForumTopicDetail/" + topicName);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String line = "";
                        StringBuilder buffer = new StringBuilder();

                        while ((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }

                        JSONObject responseJSON = new JSONObject(buffer.toString());

                        JSONObject dataJSON = new JSONObject(responseJSON.getString("data"));
                        JSONArray commentsJSON = new JSONArray(dataJSON.getString("comments"));
                        GetTopicDetailResponse response = new GetTopicDetailResponse();

                        response.returnCode = responseJSON.getInt("returnCode");
                        response.returnMessage = responseJSON.getString("returnMessage");
                        ForumTopics forumTopic = new ForumTopics();
                        forumTopic.name = dataJSON.getString("title");
                        forumTopic.description = dataJSON.getString("description");

                        List<Comment> comments = new ArrayList<>();
                        for (int i = 0; i < commentsJSON.length(); i++)
                        {
                            JSONObject obj = commentsJSON.getJSONObject(i);
                            Comment comment = new Comment();
                            comment.username = obj.getString("username");
                            comment.dateCreated = obj.getString("dateCreated");
                            comment.text = obj.getString("text");

                            comments.add(comment);
                        }
                        forumTopic.comments = comments;

                        response.data = forumTopic;

                        connection.disconnect();

                        Message msg = new Message();
                        msg.obj = response;
                        handler.sendMessage(msg);


                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }

                }
        );
    }

    public void addComment(ExecutorService srv, Handler handler, String appKey, String topicTitle, String text)
    {
        srv.execute(() -> {
            try {
                URL url = new URL(basePath + "AddComment");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputData = new JSONObject();


                outputData.put("topicTitle", topicTitle);
                outputData.put("appKey", appKey);
                outputData.put("text", text);



                BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }

                JSONObject responseJSON = new JSONObject(buffer.toString());

                Message msg = new Message();

                Response response = new Response();
                response.returnCode = responseJSON.getInt("returnCode");
                response.returnMessage = responseJSON.getString("returnMessage");

                msg.obj = response;

                handler.sendMessage(msg);

            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
    }
}
