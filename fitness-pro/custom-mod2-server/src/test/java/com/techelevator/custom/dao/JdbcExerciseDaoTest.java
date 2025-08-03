package com.techelevator.custom.dao;

import com.techelevator.custom.model.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcExerciseDaoTest extends BaseDaoTest {

    private static final Exercise EXERCISE_1 = new Exercise(1,"Cardio", "Morning Run", 5, "A 5km run to start your day.");
    private static final Exercise EXERCISE_2 = new Exercise(2,"Strength", "Push-Up Challenge", 4, "A set of increasing difficulty push-ups.");
    private static final Exercise EXERCISE_3 = new Exercise(3,"Flexibility", "Yoga Session", 5,"A 30-minute yoga session for relaxation.");

    private JdbcExerciseDao dao;
    private final List<Exercise> expectedExercises = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        dao = new JdbcExerciseDao(dataSource);
        expectedExercises.add(EXERCISE_1);
        expectedExercises.add(EXERCISE_2);
        expectedExercises.add(EXERCISE_3);
    }

    @Test
    public void getAllExercises_should_return_all_exercises() {
        List<Exercise> actualExercises = dao.getAllExercises();

        assertTrue(actualExercises.contains(EXERCISE_1));
        assertEquals(expectedExercises.size(), actualExercises.size(), "The correct amount of exercises were not returned.");
        assertEquals("Cardio", actualExercises.get(0).getCategory());
        assertEquals("Morning Run", actualExercises.get(0).getWorkoutName());
        assertEquals("A 5km run to start your day.", actualExercises.get(0).getDescription());
        assertNotNull(dao.getAllExercises());

    }

    @Test
    public void getExerciseById_should_return_correct_exercise() {
        assertNotNull(dao.getExerciseById(1), "Should not return null.");
        assertEquals(EXERCISE_1.getId(), dao.getExerciseById(1).getId(), "The correct exercise was not returned.");
    }

    @Test
    public void getExercisesByWorkoutName_should_return_correct_exercise() {
        assertNotNull(dao.getExerciseByWorkoutName("Morning Run"), "Should not return null.");
        assertEquals(EXERCISE_1, dao.getExerciseByWorkoutName("morning run"));

    }

    @Test
    public void getExercisesByCategory_should_return_correct_exercises() {
        List<Exercise> actualExercises = dao.getExercisesByCategory("cardio");

        assertEquals(expectedExercises.get(0).getCategory(), actualExercises.get(0).getCategory(), "The correct category was not returned.");
        assertEquals(expectedExercises.get(0).getWorkoutName(), actualExercises.get(0).getWorkoutName(), "The correct workout was not returned.");

    }

    @Test
    public void createExercise_creates_exercise() {
        Exercise exercise4 = new Exercise(4,"Cardio", "Treadmill", 5, "A quick run to burn calories.");
        Exercise createdExercise = dao.createExercise(exercise4);
        int newId = createdExercise.getId();
        Exercise retrievedExercise = dao.getExerciseById(newId);

        assertEquals(createdExercise.getId(), retrievedExercise.getId(), "There was an issue creating the exercise");

    }

    @Test
    public void updateExercise_updates_exercise() {
        EXERCISE_1.setDescription("A machine with a conveyor belt suitable for walking or running");
        EXERCISE_1.setWorkoutName("Incline Treadmill");
        Exercise updatedExercise = dao.updateExercise(EXERCISE_1);

        assertNotNull(EXERCISE_1);
        assertNotNull(updatedExercise);
        assertEquals(EXERCISE_1.getWorkoutName(), updatedExercise.getWorkoutName(), "There was a issue updating the exercise");

    }

    @Test
    public void deleteWorkoutExerciseById_deletes_exercise() {
        int rowAffected = dao.deleteWorkoutExerciseById(3);
        assertEquals(1, rowAffected, "The amount of rows deleted does not match up.");
        Exercise retrievedExercise = dao.getExerciseById(3);
        assertNull(retrievedExercise);

    }

}
