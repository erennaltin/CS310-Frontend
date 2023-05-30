package com.example.test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


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
import java.util.concurrent.ExecutorService;

public class UserRepository {
    private static final String basePath = "http://10.51.52.171:8080/User/";
    public void testServer(ExecutorService srv, Handler handler)
    {
        srv.submit(() -> {
                try {
                    Log.i("DEV", "start method");

                    URL url = new URL("/Register");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    Log.i("DEV", "create connection");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    Log.i("DEV", "create reader");

                    String line = "";
                    StringBuilder buffer = new StringBuilder();

                    Log.i("DEV", "create buffer");

                    while ((line = reader.readLine()) != null)
                    {
                        Log.i("DEV", "take string");

                        buffer.append(line);
                    }

                    Log.i("DEV", buffer.toString());
                    connection.disconnect();

                    Message msg = new Message();

                    msg.obj = buffer.toString();
                    handler.sendMessage(msg);


                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        );
    }

    public void register(ExecutorService srv, Handler handler, String appKey)
    {
        srv.execute(() -> {
            try {
                URL url = new URL(basePath + "Register");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputData = new JSONObject();
                outputData.put("appKey", appKey);

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

    public void updateUser(ExecutorService srv, Handler handler, User user)
    {
        srv.execute(() -> {
            try {
                URL url = new URL(basePath + "UpdateUser");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputData = new JSONObject();
                String[] parts = user.getFullName().split("\\s+(?=[^\\s]+$)");

                String firstName = parts[0];
                String lastName = parts[parts.length - 1];
                outputData.put("firstName", firstName);
                outputData.put("lastName", lastName);
                outputData.put("weight", user.getWeight());
                outputData.put("height", user.getHeight());
                outputData.put("appKey", user.getAppKey());
                outputData.put("experienceLevel", user.getExperienceLevel());

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
