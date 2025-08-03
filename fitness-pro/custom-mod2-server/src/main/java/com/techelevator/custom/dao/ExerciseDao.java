package com.techelevator.custom.dao;

import com.techelevator.custom.model.Exercise;

import java.util.List;

public interface ExerciseDao {

    List<Exercise> getAllExercises();

    Exercise getExerciseById(int id);

    Exercise getExerciseByWorkoutName(String workoutName);

    List<Exercise> getFilteredExercisesByWorkoutName(String workoutName);

    List<Exercise> getExercisesByCategory(String catgeory);

    Exercise createExercise(Exercise exercise);

    Exercise updateExercise(Exercise exercise);

    int deleteWorkoutExerciseById(int id);

}
