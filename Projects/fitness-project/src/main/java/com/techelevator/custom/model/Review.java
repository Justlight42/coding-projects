package com.techelevator.custom.model;

import java.util.Objects;

public class Review {

    private int reviewId;
    private int exerciseId;
    private int userId;
    private int rating;
    private String comment;

    public Review(int reviewId, int exerciseId, int userId, int rating, String comment) {
        this.reviewId = reviewId;
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review(int exerciseId, int userId, int rating, String comment) {
        this.exerciseId = exerciseId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public Review() {

    }

    public int getReviewId() {
        return reviewId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId && exerciseId == review.exerciseId && userId == review.userId && rating == review.rating && Objects.equals(comment, review.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, exerciseId, userId, rating, comment);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", exerciseId=" + exerciseId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

}