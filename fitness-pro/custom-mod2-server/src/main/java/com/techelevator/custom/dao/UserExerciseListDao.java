package com.techelevator.custom.dao;

import com.techelevator.custom.dto.ExerciseListEntry;
import com.techelevator.custom.model.Exercise;
import com.techelevator.custom.model.UserExerciseList;

import java.util.List;

public interface UserExerciseListDao {

    UserExerciseList getListById(int listId);

    UserExerciseList createList(int userId, String listName);

    List<Exercise> getAllExercisesInList(int listId);

    List<UserExerciseList> getListByUserId(int userId);

    List<ExerciseListEntry> getListsByExerciseId(int exerciseId);

    Exercise addExerciseToList(ExerciseListEntry exerciseListEntry);

    UserExerciseList updateListName(UserExerciseList userExerciseList);

    int removeExerciseFromList(int listId, int exerciseId);

    int deleteList(int listId);

    boolean exerciseExistInList(int listId, int exerciseId);
}
