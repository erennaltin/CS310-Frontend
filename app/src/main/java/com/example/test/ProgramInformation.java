package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.ProgramInformationBinding;

import java.util.List;

public class ProgramInformation extends AppCompatActivity {
    ProgramInformationBinding binding;
    ProgramRepository programRepo = new ProgramRepository();
    RecyclerView recyclerView;
    List<ProgramExercise> data;

    Handler getProgramHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            GetProgramResponse response = (GetProgramResponse) msg.obj;
            data = response.programExercises;

            ProgramExerciseAdaptor adp = new ProgramExerciseAdaptor(ProgramInformation.this, data);

            recyclerView.setAdapter(adp);

            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProgramInformationBinding.inflate(getLayoutInflater());
        SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("FULLNAME", null);
        String appKey = sharedPreferences.getString("APPKEY", null);
        binding.firstName.setText(name);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.programExerciseRecView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        programRepo.getProgram(((WebService)getApplication()).getSrv(), getProgramHandler, appKey);
        binding.forumButton.setOnClickListener(v -> {
            Intent i = new Intent(this, ForumTopicsActivity.class);
            startActivity(i);
        });

        ;
    }
}
