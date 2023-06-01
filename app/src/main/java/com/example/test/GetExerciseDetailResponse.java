package com.example.test;

import java.util.List;

public class GetExerciseDetailResponse extends Response{
    Exercise exercise;
    List<ExerciseRecord> exerciseRecords;

    public GetExerciseDetailResponse() {}

    public GetExerciseDetailResponse(Exercise exercise, List<ExerciseRecord> exerciseRecords) {
        this.exercise = exercise;
        this.exerciseRecords = exerciseRecords;
    }

    public GetExerciseDetailResponse(String returnMessage, int returnCode, Exercise exercise, List<ExerciseRecord> exerciseRecords) {
        super(returnMessage, returnCode);
        this.exercise = exercise;
        this.exerciseRecords = exerciseRecords;
    }
}
