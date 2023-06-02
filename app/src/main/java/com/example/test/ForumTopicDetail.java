package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.TopicDetailBinding;

import java.util.List;

public class ForumTopicDetail extends AppCompatActivity {
    TopicDetailBinding binding;
    String topicName;
    ForumTopicRepository forumTopicRepository = new ForumTopicRepository();
    ForumTopics data;
    RecyclerView recyclerView;
    String appKey;
    Handler getTopicDetailHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            GetTopicDetailResponse response = (GetTopicDetailResponse) msg.obj;

            data = response.data;

            binding.topicDetailTitle.setText(data.name);
            binding.topicDetailDescripton.setText(data.description);

            getSupportActionBar().setTitle(data.name);

            CommentAdapter adp = new CommentAdapter(ForumTopicDetail.this, data.comments);

            recyclerView.setAdapter(adp);

            return true;
        }
    });

    Handler addCommentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Response response = (Response) msg.obj;
            if (response.returnCode == 200){
                binding.commentInput.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                forumTopicRepository.getTopicDetail(((WebService)getApplication()).getSrv(), getTopicDetailHandler, topicName );
            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TopicDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
        appKey = sharedPreferences.getString("APPKEY", null);
        Intent i = getIntent();
        topicName = i.getStringExtra("topicName");
        getSupportActionBar().setTitle(topicName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.topicCommentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forumTopicRepository.getTopicDetail(((WebService)getApplication()).getSrv(), getTopicDetailHandler, topicName );
        binding.addCommentButton.setOnClickListener(v -> {
            String commentText = binding.commentInput.getText().toString();

            forumTopicRepository.addComment(((WebService)getApplication()).getSrv(), addCommentHandler, appKey, topicName, commentText);
        });
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
