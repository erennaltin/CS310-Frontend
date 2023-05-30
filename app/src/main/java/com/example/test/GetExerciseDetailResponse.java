package com.example.test;

public class GetExerciseDetailResponse extends Response{
    Exercise exercise;

    public GetExerciseDetailResponse() {}

    public GetExerciseDetailResponse(Exercise exercise) {
        this.exercise = exercise;
    }

    public GetExerciseDetailResponse(String returnMessage, int returnCode, Exercise exercise) {
        super(returnMessage, returnCode);
        this.exercise = exercise;
    }
}
