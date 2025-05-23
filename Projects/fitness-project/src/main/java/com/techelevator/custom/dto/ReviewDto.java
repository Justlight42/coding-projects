package com.techelevator.custom.dto;

public class ReviewDto {

    private int reviewId;
    private String username;
    private String workoutName;
    private int rating;
    private String comment;

    public ReviewDto(int reviewId, String username, String workoutName, int rating, String comment) {
        this.reviewId = reviewId;
        this.username = username;
        this.workoutName = workoutName;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getUsername() {
        return username;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

}
