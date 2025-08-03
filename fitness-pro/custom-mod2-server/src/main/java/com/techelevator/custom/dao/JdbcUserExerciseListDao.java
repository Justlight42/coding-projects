package com.techelevator.custom.dao;

import com.techelevator.custom.dto.ExerciseListEntry;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Exercise;
import com.techelevator.custom.model.UserExerciseList;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserExerciseListDao implements UserExerciseListDao {

    private final String USER_EXERCISE_LIST_SELECT = "Select * from user_exercise_list ";
    private final String EXERCISE_SELECT = "Select * from exercise ";
    private final JdbcTemplate jdbcTemplate;
    private final ExerciseDao exerciseDao;

    public JdbcUserExerciseListDao(DataSource dataSource, ExerciseDao exerciseDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.exerciseDao = exerciseDao;
    }

    @Override
    public UserExerciseList getListById(int listId) {
        try {
            String sql = USER_EXERCISE_LIST_SELECT + "WHERE list_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, listId);
            if (rowSet.next()) {
                return mapRowToUserExerciseList(rowSet);
            } else {
                throw new DaoException("List with ID: " + listId + " is not found.");
            }

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
    }

    @Override
    public UserExerciseList createList(int userId, String listName) {
        try {
            String sql = "INSERT INTO user_exercise_list (user_id, list_name) " +
                    "VALUES (?, ?) " +
                    "RETURNING list_id";
            int listId = jdbcTemplate.queryForObject(sql, int.class, userId, listName);
            return new UserExerciseList(listId, userId, listName);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("unique_user_listname")) {
                throw new DaoException(listName + " already exist in your exercise list.");
            }
            throw new DaoException("Error creating list " + e);
        }
    }

    @Override
    public List<Exercise> getAllExercisesInList(int listId) {
        List<Exercise> exerciseList = new ArrayList<>();
        try {
            String sql = "SELECT ex.exercise_id, ex.category, ex.workout_name, ex.description, AVG(re.rating) AS avg_rating " +
                    "FROM exercise ex JOIN exercise_list el ON ex.exercise_id = el.exercise_id " +
                    "LEFT JOIN review re ON ex.exercise_id = re.exercise_id " +
                    "WHERE el.list_id = ? GROUP BY ex.exercise_id, ex.category, ex.workout_name, ex.description " +
                    "ORDER BY ex.category";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, listId);
            while (rowSet.next()) {
                exerciseList.add(mapRowToExercise(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return exerciseList;
    }

    @Override
    public List<UserExerciseList> getListByUserId(int userId) {
        List<UserExerciseList> userList = new ArrayList<>();
        try {
            String sql = "SELECT uel.list_id, uel.user_id, uel.list_name FROM user_exercise_list uel WHERE uel.user_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                userList.add(mapRowToUserExerciseList(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return userList;
    }

    @Override
    public List<ExerciseListEntry> getListsByExerciseId(int exerciseId) {
        List<ExerciseListEntry> lists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM exercise_list WHERE exercise_id = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, exerciseId);
            while (rowSet.next()) {
                lists.add(mapRowToExerciseList(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return lists;
    }

    @Override
    public Exercise addExerciseToList(ExerciseListEntry entry) {
        try {
            String sql = "INSERT INTO exercise_list (list_id, exercise_id) " +
                    "VALUES (?, ?) " +
                    "RETURNING exercise_id";
            int id = jdbcTemplate.queryForObject(sql, int.class, entry.getListId(), entry.getExerciseId());
            return exerciseDao.getExerciseById(id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
    }

    @Override
    public UserExerciseList updateListName(UserExerciseList userExerciseList) {
        try {
            String sql = "UPDATE user_exercise_list SET list_name = ? WHERE list_id = ?;";
            jdbcTemplate.update(sql, userExerciseList.getListName(), userExerciseList.getListId());
            return getListById(userExerciseList.getListId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
    }

    @Override
    public int removeExerciseFromList(int listId, int exerciseId) {
        try {
            String sql = "DELETE FROM exercise_list WHERE list_id = ? AND exercise_id = ?";
            return jdbcTemplate.update(sql , listId, exerciseId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting exercise " + exerciseId, e);
        }
    }

    @Override
    public int deleteList(int listId) {
        try {
            String exerciseListSql = "DELETE FROM exercise_list WHERE list_id = ?;";
            String userExerciseSql = "DELETE FROM user_exercise_list WHERE list_id = ?";
            jdbcTemplate.update(exerciseListSql, listId);
            return jdbcTemplate.update(userExerciseSql, listId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting list " + listId, e);
        }
    }

    @Override
    public boolean exerciseExistInList(int listId, int exerciseId) {
        String sql = "SELECT COUNT(*) FROM exercise_list WHERE list_id = ? AND exercise_id = ?";
        int counter = jdbcTemplate.queryForObject(sql, int.class, listId, exerciseId);
        return counter > 0;
    }

    public static UserExerciseList mapRowToUserExerciseList(SqlRowSet rowSet) {
        int listId = rowSet.getInt("list_id");
        int userId = rowSet.getInt("user_id");
        String listName = rowSet.getString("list_name");
        return new UserExerciseList(listId, userId, listName);
    }

    public static ExerciseListEntry mapRowToExerciseList(SqlRowSet rowSet) {
        int listId = rowSet.getInt("list_id");
        int exerciseId = rowSet.getInt("exercise_id");
        return new ExerciseListEntry(listId, exerciseId);
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
