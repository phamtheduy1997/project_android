package com.example.a7_minutes_workout.model;

public class Workout {
    private int idImage;
    private String name;

    public Workout(int idImage, String name) {
        this.idImage = idImage;
        this.name = name;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
