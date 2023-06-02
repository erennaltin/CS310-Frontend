package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ForumTopicsActivity extends AppCompatActivity {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            //ForumTopicResponse response = (ForumTopicResponse) msg.obj;
            //data = response.forumTopics;
            data = (List<ForumTopics>)msg.obj;


            ForumTopicsAdapter adp = new ForumTopicsAdapter(ForumTopicsActivity.this,data);

            recView.setAdapter(adp);

            return true;
        }
    });

    List<ForumTopics> data;
    RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_topics);

        recView = findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().hide();
        ExecutorService srv = ((WebService)getApplication()).srv;

        ForumTopicRepository repo = new ForumTopicRepository();
        repo.getTopic(srv,handler);

    }
}