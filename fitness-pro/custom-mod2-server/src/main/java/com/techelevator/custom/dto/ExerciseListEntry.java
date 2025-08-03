package com.techelevator.custom.dto;

public class ExerciseListEntry {

    private int listId;
    private int exerciseId;

    public ExerciseListEntry(int listId, int exerciseId) {
        this.listId = listId;
        this.exerciseId = exerciseId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

}
