package com.example.test;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName;
    private int weight;
    private int height;
    private ExperienceLevel experienceLevel;
    private String appKey;

    public User() {
        // TODO Auto-generated constructor stub
    }

    public User(String appKey) {
        super();
        this.appKey = appKey;
    }

    public User(String fullName, int weight, int height, String appKey, ExperienceLevel experienceLevel) {
        super();
        this.fullName = fullName;
        this.weight = weight;
        this.height = height;
        this.appKey = appKey;
        this.experienceLevel = experienceLevel;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public BMI calculateBMI()
    {
        double calculatedBMI = weight / Math.pow((double) height / 100, 2);

        if (calculatedBMI <= 18.5)
        {
            return BMI.UNDERWEIGHT;
        }
        else if (18.5 < calculatedBMI && calculatedBMI <= 25)
        {
            return BMI.NORMAL;
        }
        else if (25 < calculatedBMI && calculatedBMI <= 30)
        {
            return BMI.OVERWEIGHT;
        }
        else
        {
            return BMI.OBESITY;
        }
    }
}
