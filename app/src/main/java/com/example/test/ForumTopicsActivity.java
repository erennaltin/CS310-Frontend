package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

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
        getSupportActionBar().setTitle("GymDuo. Forum");
        ExecutorService srv = ((WebService)getApplication()).srv;

        ForumTopicRepository repo = new ForumTopicRepository();
        repo.getTopic(srv,handler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Handle the back button click
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}