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
                        JSONArray exerciseRecordsJSON = new JSONArray(data.getString("exerciseRecords"));
                        List<ExerciseRecord> exerciseRecords = new ArrayList<>();

                        for (int i = 0; i < exerciseRecordsJSON.length(); i++)
                        {
                            JSONObject obj = exerciseRecordsJSON.getJSONObject(i);
                            ExerciseRecord exerciseRecord = new ExerciseRecord();
                            exerciseRecord.maxRep = obj.getString("maxRep");
                            exerciseRecord.weight = obj.getString("weight");
                            exerciseRecord.date = obj.getString("date");

                            exerciseRecords.add(exerciseRecord);
                        }


                        Exercise exercise = new Exercise();
                        exercise.name = data.getString("name");
                        exercise.description = data.getString("description");
                        exercise.videoURL = data.getString("videoURL");
                        exercise.photoURL = data.getString("photoURL");
                        response.exercise = exercise;
                        response.exerciseRecords = exerciseRecords;

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

    public void addExerciseRecord(ExecutorService srv, Handler handler,String exerciseId,  int weight, int rep, String appKey)
    {
        srv.execute(() -> {
            try {
                URL url = new URL(basePath + "AddExerciseRecord");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputData = new JSONObject();


                outputData.put("exerciseId", exerciseId);
                outputData.put("appKey", appKey);
                outputData.put("weight", weight);
                outputData.put("maxRep", rep);
                outputData.put("currentWeight", 0);


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
