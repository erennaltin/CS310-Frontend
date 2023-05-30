package com.example.test;

public class ProgramExercise {
    public String name;
    public String description;
    public String photoURL;
    public String videoURL;
    public int repAmount;
    public int setAmount;
    public String exerciseId;

    ProgramExercise(){}

    ProgramExercise(String name, String description, String photoURL, String videoURL, int repAmount, int setAmount, String exerciseId)
    {
        this.name = name;
        this.description = description;
        this.photoURL = photoURL;
        this.videoURL = videoURL;
        this.repAmount = repAmount;
        this.setAmount = setAmount;
        this.exerciseId = exerciseId;
    }

}
