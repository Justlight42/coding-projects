package com.techelevator.custom.dao;

import com.techelevator.custom.dto.ReviewDto;
import com.techelevator.custom.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcReviewDaoTest extends BaseDaoTest {

    private static final Review REVIEW_1 = new Review(1, 1, 1, 5, "Great way to start the day!");
    private static final Review REVIEW_2 = new Review(2, 2, 2, 4, "Challenging but rewarding workout.");
    private static final Review REVIEW_3 = new Review(3, 3, 3, 5, "Felt very relaxed after this session.");
    private static final ReviewDto REVIEWDTO_1 = new ReviewDto(1, "user1", "Morning Run", 5, "Great way to start the day!");
    private static final ReviewDto REVIEWDTO_2 = new ReviewDto(2, "user2", "Push-Up Challenge", 4, "Challenging but rewarding workout.");
    private static final ReviewDto REVIEWDTO_3 = new ReviewDto(3, "user3", "Yoga Session", 5, "Felt very relaxed after this session.");

    private JdbcReviewDao dao;
    private List<Review> reviews = new ArrayList<>();
    private List<ReviewDto> reviewsDto = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        dao = new JdbcReviewDao(dataSource);
        reviews.add(REVIEW_1);
        reviews.add(REVIEW_2);
        reviews.add(REVIEW_3);
        reviewsDto.add(REVIEWDTO_1);
        reviewsDto.add(REVIEWDTO_2);
        reviewsDto.add(REVIEWDTO_3);
    }

    @Test
    public void getAllReviews_should_return_all_reviews() {
        List<ReviewDto> actualReviews = dao.getAllReviews();

        assertEquals(reviewsDto.size(), actualReviews.size(), "The correct amount of reviews were not returned.");
        assertEquals("user1", actualReviews.get(0).getUsername());
        assertEquals("Morning Run", actualReviews.get(0).getWorkoutName());
        assertEquals(5, actualReviews.get(0).getRating());
        assertNotNull(dao.getAllReviews());

    }

    @Test
    public void getReviewsById_should_return_correct_review() {
        assertNotNull(dao.getReviewsById(1), "Should not return null.");
        assertEquals(REVIEW_1.getReviewId(), dao.getReviewsById(1).getReviewId(), "The correct review was not returned.");

    }

    @Test
    public void getReviewsByRating_should_return_correct_filtered_reviews() {
        List<ReviewDto> actualReviews = dao.getReviewsByRating(3);

        assertNotNull(dao.getReviewsByRating(3));
        assertEquals(3, actualReviews.size(), "The correct amount of reviews was not returned.");
        assertEquals(reviews.get(0).getRating(), actualReviews.get(0).getRating());
        assertEquals(reviews.get(1).getRating(), actualReviews.get(1).getRating());

    }

    @Test
    public void getReviewsByUserId_should_return_correct_reviews() {
        List<Review> actualReviews = dao.getReviewsByUserId(2);

        assertNotNull(dao.getReviewsByUserId(2));
        assertEquals("Challenging but rewarding workout.", actualReviews.get(0).getComment());

    }

    @Test
    public void getRatingAverageByExerciseId_should_return_correct_average_of_ratings() {
        assertEquals(5, dao.getRatingAverageByExerciseId(1), "An incorrect rating sum was returned.");

    }

    @Test
    public void createReview_creates_review() {
        Review review4 = new Review(4,2, 2, 2,"Workout did not feel like it hit the right muscles.");
        Review createdReview = dao.createReview(review4);
        int newId = createdReview.getReviewId();
        Review retrievedExercise = dao.getReviewsById(newId);

        assertEquals(createdReview.getReviewId(), retrievedExercise.getReviewId(), "There was an issue creating the review");

    }

    @Test
    public void updateReview_updates_review() {
        REVIEW_1.setRating(3);
        REVIEW_1.setComment("Distance felt way to long");
        Review updateReview = dao.updateReview(REVIEW_1);

        assertNotNull(REVIEW_1);
        assertNotNull(updateReview);
        assertEquals(REVIEW_1.getRating(), updateReview.getRating(), "There was a issue updating the review");
        assertEquals(REVIEW_1.getComment(), updateReview.getComment(), "There was a issue updating the review");

    }

    @Test
    public void deleteReview_deletes_review() {
        int rowAffected = dao.deleteReview(2, 2, false);
        assertEquals(1, rowAffected, "The amount of rows deleted does not match up.");
        Review retrievedReview = dao.getReviewsById(2);
        assertNull(retrievedReview);

    }


}
