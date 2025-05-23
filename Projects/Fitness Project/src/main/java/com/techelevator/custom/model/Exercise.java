package com.techelevator.custom.model;

import java.util.Objects;

public class Exercise {

    private int id;
    private String category;
    private String workoutName;
    private String description;
    private double rating;

//    public Exercise(int id, String category, String workoutName, String description) {
//        this.id = id;
//        this.category = category;
//        this.workoutName = workoutName;
//        this.description = description;
//    }

    public Exercise(int id, String category, String workoutName, double rating, String description) {
        this.id = id;
        this.category = category;
        this.workoutName = workoutName;
        this.rating = rating;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id && Objects.equals(category, exercise.category) && Objects.equals(workoutName, exercise.workoutName) && Objects.equals(description, exercise.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, workoutName, description);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", workoutName='" + workoutName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
