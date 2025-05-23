package com.techelevator.custom.controller;

import com.techelevator.custom.dao.ExerciseDao;
import com.techelevator.custom.dto.ExerciseDBDto;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Exercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/exercise")
public class ExerciseController {

    private final ExerciseDao exerciseDao;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXERCISEDB_API_KEY = "?rapidapi-key=57eb6b9058mshcc30d5278379a2ep1c2a30jsn502148a9ae5d";
    private final String url = "https://exercisedb.p.rapidapi.com";

    public ExerciseController(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    @GetMapping
    public List<Exercise> getAllExercises() {
        try {
            return exerciseDao.getAllExercises();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get exercises: " + e.getMessage());
        }
    }

    @GetMapping(path = "/category/{category}")
    public List<Exercise> getExercisesByCategory(@PathVariable String category) {
        try {
            List<Exercise> categoryExercises = exerciseDao.getExercisesByCategory(category);
            if (categoryExercises.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not get exercises from: " + category);
            }
            return categoryExercises;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get exercises: " + e.getMessage());
        }
    }

    @GetMapping(path = "/workoutName/{workoutName}")
    public Exercise getExerciseByWorkoutName(@PathVariable String workoutName) {
        try {
            Exercise workout = exerciseDao.getExercisesByWorkoutName(workoutName);
            if (workout == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find exercise: " + workoutName);
            }
            return workout;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get exercises: " + e.getMessage());
        }
    }

    @GetMapping(path = "/{workoutName}/details")
    public ExerciseDBDto getDetailedExercise(@PathVariable String workoutName) {
        Exercise exercise = exerciseDao.getExercisesByWorkoutName(workoutName);
        if (exercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find exercise with the workout name: " + workoutName);
        }

        String newUrl = url + "/exercises/name/" + exercise.getWorkoutName().toLowerCase() + EXERCISEDB_API_KEY;

        ResponseEntity<ExerciseDBDto[]> responseEntity = restTemplate.getForEntity(newUrl, ExerciseDBDto[].class);

        ExerciseDBDto[] exercises = responseEntity.getBody();

        return exercises.length == 0 ? null : exercises[0];

    }

//    @GetMapping(path = "/bodyPart")
//    public Map<String, List<Exercise>> getExercisesFilteredByMuscle() {
//        String newUrl = url + "/exercises/" + EXERCISEDB_API_KEY;
//
//        ResponseEntity<ExerciseDBDto[]> responseEntity = restTemplate.getForEntity(newUrl, ExerciseDBDto[].class);
//        ExerciseDBDto[] dbExercises = responseEntity.getBody();
//
//        Map<String, List<Exercise>> filteredExercises = new HashMap<>();
//
//        for (Exercise exercise : exerciseDao.getAllExercises()) {
//            for (ExerciseDBDto dbExercise : dbExercises) {
//                if (dbExercise.getName().toLowerCase().contains(exercise.getWorkoutName().toLowerCase())) {
//                    String bodyPart = dbExercise.getBodyPart();
//                    List<Exercise> exerciseBodyPart = filteredExercises.get(bodyPart);
//                    if (exerciseBodyPart == null) {
//                        exerciseBodyPart = new ArrayList<>();
//                        filteredExercises.put(bodyPart, exerciseBodyPart);
//                    }
//                    exerciseBodyPart.add(exercise);
//                    break;
//                }
//            }
//        }
//        return filteredExercises;
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Exercise createExercise(@RequestBody Exercise exercise) {
        formatException(exercise);
        Exercise createdExercise = exerciseDao.createExercise(exercise);
        if (createdExercise == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create an exercise.");
        }
        return createdExercise;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{exerciseId}")
    public Exercise updateExercise(@RequestBody Exercise exercise, @PathVariable int exerciseId) {
        formatException(exercise);
        Exercise existingExercise = exerciseDao.getExerciseById(exerciseId);
        if (existingExercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This exerciseId does not exist: " + exerciseId);
        }
        Exercise updatedExercise = new Exercise(exerciseId, exercise.getCategory(), exercise.getWorkoutName(), exercise.getRating(), exercise.getDescription());
        return exerciseDao.updateExercise(updatedExercise);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteExercise(@PathVariable int id) {
        Exercise exercise = exerciseDao.getExerciseById(id);
        if (exercise == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to delete an exercise.");
        }
        exerciseDao.deleteWorkoutExerciseById(id);
    }

    private void formatException(Exercise exercise) {
        if (exercise == null || exercise.getCategory() == null || exercise.getCategory().isEmpty() ||
                exercise.getWorkoutName() == null || exercise.getWorkoutName().isEmpty() ||
                exercise.getDescription() == null || exercise.getDescription().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Exercise was not formatted correctly");
        }
    }


}
