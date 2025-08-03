package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Exercise;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcExerciseDao implements ExerciseDao {

    public final String EXERCISE_SELECT = "SELECT ex.exercise_id, ex.category, ex.workout_name, ex.description, AVG(re.rating) AS avg_rating " +
            "FROM exercise ex LEFT JOIN review re ON ex.exercise_id = re.exercise_id ";
    public final String EXERCISE_SELECT_GROUP = " GROUP BY ex.exercise_id, ex.category, ex.workout_name, ex.description ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcExerciseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String sql = EXERCISE_SELECT + EXERCISE_SELECT_GROUP + "ORDER BY category;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
            while (rowSet.next()) {
                exercises.add(mapRowToExercise(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return exercises;

    }

    @Override
    public Exercise getExerciseById(int id) {
        try {
            String sql = EXERCISE_SELECT + "WHERE ex.exercise_id = ?" + EXERCISE_SELECT_GROUP;
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                return mapRowToExercise(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;

    }

    @Override
    public Exercise getExerciseByWorkoutName(String workoutName) {
        try {
            String sql = EXERCISE_SELECT + "WHERE LOWER(ex.workout_name) = LOWER(?)" + EXERCISE_SELECT_GROUP;
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, workoutName);
            if (rowSet.next()) {
                return mapRowToExercise(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;

    }

    @Override
    public List<Exercise> getFilteredExercisesByWorkoutName(String workoutName) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String sql = EXERCISE_SELECT + "WHERE LOWER(ex.workout_name) LIKE LOWER(?)" + EXERCISE_SELECT_GROUP;
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, "%" + workoutName + "%");
            while (rowSet.next()) {
                exercises.add(mapRowToExercise(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return exercises;

    }

    @Override
    public List<Exercise> getExercisesByCategory(String category) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String sql = EXERCISE_SELECT + "WHERE LOWER(ex.category) = LOWER(?)" + EXERCISE_SELECT_GROUP;
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, category);
            while (rowSet.next()) {
                exercises.add(mapRowToExercise(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return exercises;

    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        try {
            String sql = "INSERT INTO exercise (category, workout_name, description) " +
                    "VALUES (?, ?, ?) " +
                    "RETURNING exercise_id";
            int exerciseId = jdbcTemplate.queryForObject(sql, int.class, exercise.getCategory(), exercise.getWorkoutName(), exercise.getDescription());
            return getExerciseById(exerciseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating exercise " + exercise, e);
        }

    }

    @Override
    public Exercise updateExercise(Exercise exercise) {
        try {
            String sql = "UPDATE exercise SET category = ?, workout_name = ?, description = ? WHERE exercise_id = ?;";
            int rowsAffected = jdbcTemplate.update(sql, exercise.getCategory(), exercise.getWorkoutName(), exercise.getDescription(), exercise.getId());
            if (rowsAffected > 0) {
                return getExerciseById(exercise.getId());
            } else {
                return null;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating exercise " + exercise, e);
        }

    }

    @Override
    public int deleteWorkoutExerciseById(int id) {
        try {
            String sqlExercise = "DELETE FROM exercise WHERE exercise_id = ?;";
            String sqlReview = "DELETE FROM review WHERE exercise_id = ?;";
            String sqlUserExercise = "DELETE FROM user_exercise WHERE exercise_id = ?;";
            String sqlExerciseList = "DELETE FROM exercise_list WHERE exercise_id = ?";
            jdbcTemplate.update(sqlReview, id);
            jdbcTemplate.update(sqlUserExercise, id);
            jdbcTemplate.update(sqlExerciseList, id);
            return jdbcTemplate.update(sqlExercise, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting workout " + id, e);
        }

    }

    private static Exercise mapRowToExercise(SqlRowSet rowSet) {
        int exerciseId = rowSet.getInt("exercise_id");
        String category = rowSet.getString("category");
        String workoutName = rowSet.getString("workout_name");
        double rating = rowSet.getDouble("avg_rating");
        String description = rowSet.getString("description");
        return new Exercise(exerciseId, category, workoutName, rating, description);
    }

}
