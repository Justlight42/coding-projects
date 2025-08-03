import axios from 'axios';

const BASE_REVIEW_PATH = 'api/review'

export default {

    getAllReviews() {
        return axios.get(BASE_REVIEW_PATH);
    },
    getReviewsByUsername(username) {
        return axios.get(`${BASE_REVIEW_PATH}/username/${username}`);
    },
    getAllReviewsByExerciseID(exerciseId) {
        return axios.get(`${BASE_REVIEW_PATH}/exercise/${exerciseId}`);
    },
    getReviewsByRating(rating) {
        return axios.get(`${BASE_REVIEW_PATH}/rating/${rating}`);
    },
    createReview(review) {
        return axios.post(BASE_REVIEW_PATH, review);
    },
    updateReview(review, reviewId) {
        return axios.put(`${BASE_REVIEW_PATH}/${reviewId}`, review);
    },
    deleteReview(reviewId) {
        return axios.delete(`${BASE_REVIEW_PATH}/${reviewId}`)
    }

}