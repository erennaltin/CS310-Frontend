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

public class ProgramRepository {
    private static final String basePath = "http://10.51.52.171:8080/Program/";
    public void getProgram(ExecutorService srv, Handler handler, String appKey)
    {
        srv.submit(() -> {
                    try {

                        URL url = new URL(basePath +"GetProgram/" + appKey);

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
                        GetProgramResponse response = new GetProgramResponse();
                        response.returnCode = responseJSON.getInt("returnCode");
                        response.returnMessage = responseJSON.getString("returnMessage");

                        JSONObject data = new JSONObject(responseJSON.getString("data"));
                        JSONArray programExercisesJSON = new JSONArray(data.getString("programExercises"));
                        List<ProgramExercise> programExercises = new ArrayList<>();
                        for (int i = 0; i < programExercisesJSON.length(); i++)
                        {
                            JSONObject obj = programExercisesJSON.getJSONObject(i);
                            ProgramExercise programExercise = new ProgramExercise();
                            programExercise.exerciseId = obj.getString("exerciseId");
                            programExercise.setAmount = obj.getInt("sets");
                            programExercise.repAmount = obj.getInt("reps");
                            programExercise.videoURL = obj.getString("videoURL");
                            programExercise.photoURL = obj.getString("photoURL");
                            programExercise.name = obj.getString("name");
                            programExercise.description = obj.getString("description");
                            programExercises.add(programExercise);
                        }

                        response.programExercises = programExercises;


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
