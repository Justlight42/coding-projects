package com.techelevator.custom.dao;

import com.techelevator.custom.dto.ReviewDto;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReviewDao implements ReviewDao {

    private final String REVIEW_SELECT = "SELECT * FROM review re ";
    private final String REVIEWDTO_SELECT = "SELECT re.review_id, us.username, ex.workout_name, re.rating, re.comment FROM review re " +
            "JOIN exercise ex ON re.exercise_id = ex.exercise_id " +
            "JOIN users us ON re.user_id = us.user_id ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<ReviewDto> reviewsDto = new ArrayList<>();
        try {
            String sql = REVIEWDTO_SELECT;
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
            while (rowSet.next()) {
                reviewsDto.add(mapRowToReviewDto(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return reviewsDto;
    }

    @Override
    public Review getReviewsById(int id) {
        try {
            String sql = REVIEW_SELECT + "WHERE re.review_id = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                return mapRowToReview(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return null;

    }

    @Override
    public List<ReviewDto> getAllReviewsByExerciseId(int exerciseId) {
        List<ReviewDto> reviews = new ArrayList<>();
        try {
            String sql = REVIEWDTO_SELECT + "WHERE re.exercise_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, exerciseId);
            while (rowSet.next()) {
                reviews.add(mapRowToReviewDto(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return reviews;
    }

    @Override
    public List<ReviewDto> getReviewsByRating(int rating) {
        List<ReviewDto> reviewsDto = new ArrayList<>();
        try {
            String sql = REVIEWDTO_SELECT + "WHERE re.rating BETWEEN ? AND 5;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, rating);
            while (rowSet.next()) {
                reviewsDto.add(mapRowToReviewDto(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return reviewsDto;

    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        try {
            String sql = REVIEW_SELECT + "WHERE re.user_id = ?;";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                reviews.add(mapRowToReview(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return reviews;

    }

    @Override
    public List<ReviewDto> getReviewsByUsername(String username) {
        List<ReviewDto> reviewsDto = new ArrayList<>();
        try {
            String sql = REVIEWDTO_SELECT + " WHERE us.username = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            while (rowSet.next()) {
                reviewsDto.add(mapRowToReviewDto(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        }
        return reviewsDto;

    }

    @Override
    public double getRatingAverageByExerciseId(int exerciseId) {
        try {
            String sql = "SELECT AVG(rating) FROM review WHERE exercise_id = ?;";
            double beforeRound = jdbcTemplate.queryForObject(sql, double.class, exerciseId);
            return Math.round(beforeRound * 10.0) / 10.0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataAccessException e) {
            throw new DaoException("Error getting the avg of all review ratings for the exercise ID: " + exerciseId, e);
        }

    }

    @Override
    public Review createReview(Review review) {
        try {
            String sql = "INSERT INTO review (exercise_id, user_id, rating, comment) " +
                    "VALUES (?, ?, ?, ?) " +
                    "RETURNING review_id";
            int reviewId = jdbcTemplate.queryForObject(sql, int.class, review.getExerciseId(), review.getUserId(), review.getRating(), review.getComment());
            return getReviewsById(reviewId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error creating review " + review, e);
        }

    }

    @Override
    public Review updateReview(Review review) {
        try {
            String sql = "UPDATE review SET exercise_id = ?, user_id = ?, rating = ?, comment = ? WHERE review_id = ?;";
            jdbcTemplate.update(sql, review.getExerciseId(), review.getUserId(), review.getRating(), review.getComment(), review.getReviewId());
            return getReviewsById(review.getReviewId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error updating review " + review, e);
        }

    }

    @Override
    public int deleteReview(int reviewId, int userId, boolean isAdmin) {
        try {
            if (isAdmin) {
                String sql = "DELETE FROM review WHERE review_id = ?;";
                return jdbcTemplate.update(sql, reviewId);
            } else {
                String sql = "DELETE FROM review WHERE review_id = ? and user_id = ?;";
                return jdbcTemplate.update(sql, reviewId, userId);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Error connecting to the server " + e);
        } catch (DataAccessException e) {
            throw new DaoException("Error deleting reviews using review ID: "
                    + reviewId + " and user ID: " + userId, e);
        }

    }

    public static Review mapRowToReview(SqlRowSet rowSet) {
        int reviewId = rowSet.getInt("review_id");
        int exerciseId = rowSet.getInt("exercise_id");
        int userId = rowSet.getInt("user_id");
        int rating = rowSet.getInt("rating");
        String comment = rowSet.getString("comment");
        Review review = new Review(exerciseId, userId, rating, comment);
        review.setReviewId(reviewId);
        return review;
    }

    public static ReviewDto mapRowToReviewDto(SqlRowSet rowSet) {
        int reviewId = rowSet.getInt("review_id");
        String username = rowSet.getString("username");
        String workoutName = rowSet.getString("workout_name");
        int rating = rowSet.getInt("rating");
        String comment = rowSet.getString("comment");
        return new ReviewDto(reviewId, username, workoutName, rating, comment);
    }

}
