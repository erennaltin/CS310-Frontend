package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.MediaController;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.ExerciseDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetail extends AppCompatActivity {
    ExerciseDetailBinding binding;
    ExerciseRepository exerciseRepo = new ExerciseRepository();
    RecyclerView recyclerView;
    List<ExerciseRecord> exerciseRecordData = new ArrayList<>();
    Exercise data;
    String exerciseId;
    String appKey;
    Handler getExerciseDetailHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            GetExerciseDetailResponse response = (GetExerciseDetailResponse) msg.obj;

            data = response.exercise;
            exerciseRecordData = response.exerciseRecords;

            binding.exerciseDetailTitle.setText(data.name);
            binding.exerciseDetailDescripton.setText(data.description);
            getSupportActionBar().setTitle(data.name);

            binding.exerciseDetailVideo.getSettings().setJavaScriptEnabled(true);

            String videoId = data.videoURL;  // Replace with the YouTube video ID
            String videoUrl = "https://www.youtube.com/embed/" + videoId;

            String html = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            binding.exerciseDetailVideo.loadData(html, "text/html", "utf-8");

            ExerciseRecordAdapter adp = new ExerciseRecordAdapter(ExerciseDetail.this, exerciseRecordData);

            recyclerView.setAdapter(adp);

            return true;
        }
    });

    Handler addExerciseRecordHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Response response = (Response) msg.obj;
            if (response.returnCode == 200){
                binding.exerciseRecordRep.setText("");
                binding.exerciseRecordWeight.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                exerciseRepo.getExercise(((WebService)getApplication()).getSrv(), getExerciseDetailHandler, appKey, exerciseId);

            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ExerciseDetailBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
        appKey = sharedPreferences.getString("APPKEY", null);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        exerciseId = intent.getStringExtra("exerciseId");

        exerciseRepo.getExercise(((WebService)getApplication()).getSrv(), getExerciseDetailHandler, appKey, exerciseId);

        recyclerView = findViewById(R.id.exerciseRecordList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.addExerciseRecord.setOnClickListener(v -> {
            String weight = binding.exerciseRecordWeight.getText().toString();
            String repAmount = binding.exerciseRecordRep.getText().toString();

            exerciseRepo.addExerciseRecord(((WebService)getApplication()).getSrv(), addExerciseRecordHandler, exerciseId, Integer.parseInt(weight), Integer.parseInt(repAmount), appKey);
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
