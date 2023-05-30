package com.example.test;

import android.os.Handler;
import android.os.Message;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class ExerciseRepository {
    private static final String basePath = "http://10.51.52.171:8080/Exercise/";

    public void getExercise(ExecutorService srv, Handler handler, String appKey, String exerciseId)
    {
        srv.submit(() -> {
                    try {

                        URL url = new URL(basePath +"GetExercise/" + exerciseId + "?userAppKey=" + appKey);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String line = "";
                        StringBuilder buffer = new StringBuilder();

                        while ((line = reader.readLine()) != null)
                        {
                            buffer.append(line);
                        }

                        connection.disconnect();

                        Message msg = new Message();

                        JSONObject responseJSON = new JSONObject(buffer.toString());
                        GetExerciseDetailResponse response = new GetExerciseDetailResponse();
                        response.returnCode = responseJSON.getInt("returnCode");
                        response.returnMessage = responseJSON.getString("returnMessage");

                        JSONObject data = new JSONObject(responseJSON.getString("data"));
                        Exercise exercise = new Exercise();
                        exercise.name = data.getString("name");
                        exercise.description = data.getString("description");
                        exercise.videoURL = data.getString("videoURL");
                        exercise.photoURL = data.getString("photoURL");
                        response.exercise = exercise;

                        msg.obj = response;
                        handler.sendMessage(msg);


                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }

                }
        );
    }
}
