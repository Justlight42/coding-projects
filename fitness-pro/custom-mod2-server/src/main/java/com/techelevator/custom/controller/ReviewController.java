package com.techelevator.custom.controller;

import com.techelevator.custom.dao.ExerciseDao;
import com.techelevator.custom.dao.ReviewDao;
import com.techelevator.custom.dao.UserDao;
import com.techelevator.custom.dto.ReviewDto;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Review;
import com.techelevator.custom.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/review")
public class ReviewController {

    private final ReviewDao reviewDao;
    private final UserDao userDao;
    private final ExerciseDao exerciseDao;

    public ReviewController(ReviewDao reviewDao, UserDao userDao, ExerciseDao exerciseDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.exerciseDao = exerciseDao;
    }

    @GetMapping
    public List<ReviewDto> getAllReviews() {
        try {
            return reviewDao.getAllReviews();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get reviews: " + e.getMessage());
        }
    }

    @GetMapping(path = "/username/{username}")
    public List<ReviewDto> getReviewsByUsername(@PathVariable String username) {
        try {
            List<ReviewDto> userReviews = reviewDao.getReviewsByUsername(username);
            if (userReviews.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not get reviews from: " + username);
            }
            return userReviews;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get reviews: " + e.getMessage());
        }
    }

    @GetMapping(path = "/exercise/{exerciseId}")
    public List<ReviewDto> getAllReviewsByExerciseId(@PathVariable int exerciseId) {
        try {
            return reviewDao.getAllReviewsByExerciseId(exerciseId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get reviews: " + e.getMessage());
        }
    }

    @GetMapping(path = "/rating/{rating}")
    public List<ReviewDto> getReviewsByRating(@PathVariable int rating) {
        try {
            List<ReviewDto> userReviews = reviewDao.getReviewsByRating(rating);
            if (userReviews.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not get reviews from: " + rating + " - 5");
            }
            return userReviews;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get reviews: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Review createReview(@RequestBody Review review, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        if (exerciseDao.getExerciseById(review.getExerciseId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create a review with exercise ID of : " + review.getExerciseId());
        }
        Review newReview = new Review((review.getExerciseId()), user.getId(), review.getRating(), review.getComment());
        return reviewDao.createReview(newReview);
    }

    @PreAuthorize("isAuthenticated")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{reviewId}")
    public Review updateReview(@RequestBody Review review, @PathVariable int reviewId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        Review existingReview = reviewDao.getReviewsById(reviewId);
        if (existingReview == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This reviewId does not exist: " + reviewId);
        }
        Review updatedReview = new Review(reviewId , review.getExerciseId(), user.getId(), review.getRating(), review.getComment());
        String reviewUser = userDao.getUserById(existingReview.getUserId()).getUsername();
        if (user.getId() != existingReview.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This is not your review. This is a review from " + reviewUser);
        }
        return reviewDao.updateReview(updatedReview);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{reviewId}")
    public void deleteReview(@PathVariable int reviewId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        boolean isAdmin = user.getRole().equalsIgnoreCase("ROLE_ADMIN");
        int deletedReview = reviewDao.deleteReview(reviewId, user.getId(), isAdmin);
        if (deletedReview == 0) {
            if (!isAdmin) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this review");
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review that you tried to delete is not found.");
            }
        }
    }

}
