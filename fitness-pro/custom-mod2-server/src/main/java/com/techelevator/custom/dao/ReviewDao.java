package com.techelevator.custom.dao;

import com.techelevator.custom.dto.ReviewDto;
import com.techelevator.custom.model.Review;

import java.util.List;

public interface ReviewDao {

    List<ReviewDto> getAllReviews();

    Review getReviewsById(int id);

    List<ReviewDto> getAllReviewsByExerciseId(int exerciseId);

    List<ReviewDto> getReviewsByRating(int rating);

    List<Review> getReviewsByUserId(int userId);

    List<ReviewDto> getReviewsByUsername(String username);

    double getRatingAverageByExerciseId(int exerciseId);

    Review createReview(Review review);

    Review updateReview(Review review);

    int deleteReview(int reviewId, int userId, boolean isAdmin);
}
