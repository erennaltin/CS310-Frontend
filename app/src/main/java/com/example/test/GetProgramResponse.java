package com.example.test;

import java.util.List;

public class GetProgramResponse extends Response {
    List<ProgramExercise> programExercises;

    GetProgramResponse(){}

    public GetProgramResponse(List<ProgramExercise> programExercises) {
        this.programExercises = programExercises;
    }

    public GetProgramResponse(String returnMessage, int returnCode, List<ProgramExercise> programExercises) {
        super(returnMessage, returnCode);
        this.programExercises = programExercises;
    }
}
