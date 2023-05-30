package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.databinding.ExerciseDetailBinding;

public class ExerciseDetail extends AppCompatActivity {
    ExerciseDetailBinding binding;
    ExerciseRepository exerciseRepo = new ExerciseRepository();
    Exercise data;
    Handler getExerciseDetailHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            GetExerciseDetailResponse response = (GetExerciseDetailResponse) msg.obj;
            data = response.exercise;
            binding.exerciseDetailTitle.setText(data.name);
            binding.exerciseDetailDescripton.setText(data.description);
            binding.exerciseDetailVideo.getSettings().setJavaScriptEnabled(true);

            String videoId = data.videoURL;  // Replace with the YouTube video ID
            String videoUrl = "https://www.youtube.com/embed/" + videoId;

            String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            binding.exerciseDetailVideo.loadData(html, "text/html", "utf-8");
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ExerciseDetailBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
        String appKey = sharedPreferences.getString("APPKEY", null);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String exerciseId = intent.getStringExtra("exerciseId");
        Log.i("DEV", exerciseId);

        exerciseRepo.getExercise(((WebService)getApplication()).getSrv(), getExerciseDetailHandler, appKey, exerciseId);

    }
}
