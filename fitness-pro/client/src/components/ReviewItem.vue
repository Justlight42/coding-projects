<template>
  <div class="review-card">
      <div class="comment-pair">
      <h3>Username: {{ review.username }}</h3>
      <span class="logo-cursor" v-if="isUserReviewOwner() || adminPermission()" v-on:click.prevent="deleteReview">🗑️</span>
      </div>
      <p v-if="showWorkoutName">Exercise Name: {{ review.workoutName }}</p>
      <p>Rating: {{ review.rating }} ⭐</p>
      <p>Comment: {{ review.comment }}</p>
  </div>
</template>

<script>
import ReviewService from '../services/ReviewService';

export default {
  props: {
    showWorkoutName: {
      type: Boolean,
      default: true
    },
    review: {
      type: Object,
      required: true
    },
  },
  methods: {
    isUserReviewOwner() {
      return this.$store.state.user.username === this.review.username;
    },
    adminPermission() {
      return this.$store.state.user.role === 'ROLE_ADMIN';
    },
    deleteReview() {
      ReviewService.deleteReview(this.review.reviewId).then(() => {
        this.$store.commit("REMOVE_REVIEW", this.review)
        alert(`${this.review.workoutName} has been successfully deleted`);
      })
        .catch((error) => {
          console.log('Error occurred when deleting your review:', error)
        })
    },
  },
};
</script>

<style>

.review-card {
    display: flex;
    flex-direction: column;
    background-color: #fff;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    height: 100%;
    background: lightsteelblue;
}

.review-card h3,
.review-card p {
    margin: 0.25rem 0;
}

.comment-pair {
  display: flex;
}

.comment-pair span {
  margin-left: auto;
}

</style>