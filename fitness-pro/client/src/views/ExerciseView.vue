<template>
  <div class="exercise-card">
    <LoadingSpinner v-if="isLoading" :spin="isLoading"/>
    <div v-else>
      <h3>Exercise Name: {{ capitalizeLetter(detailedExercise.name) }}</h3>
      <p>Body Muscle Used: {{ capitalizeLetter(detailedExercise.bodyPart) }}</p>
      <p>Target: {{ capitalizeLetter(detailedExercise.target) }}</p>
      <p>Equipment Needed: {{ capitalizeLetter(detailedExercise.equipment) }}</p>
      <p>Instructions: {{ detailedExercise.instructions }}</p>
    </div>
  </div>
  <div class="review-section">
    <div class="comment-row">
    <h3>Comment Section</h3>
    <button v-on:click="routeToAddReview">Add Review</button>
    </div>
    <LoadingSpinner v-if="isLoading" :spin="isLoading"/>
    <div id="comment-area" v-else>
    <ReviewItem v-for="review in $store.state.reviews" :key="review.reviewId" :review="review" :showWorkoutName="showWorkoutName" />
    </div>
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ReviewItem from "../components/ReviewItem.vue";
import ExerciseService from "../services/ExerciseService";
import ReviewService from "../services/ReviewService";

export default {
  components: {
    LoadingSpinner,
    ReviewItem
  },

  data() {
    return {
      isLoading: true,
      detailedExercise: {},
      reviews: [],
      showWorkoutName: false
    };
  },
  methods: {
    getDetailedExercise() {
      ExerciseService.getDetailedExercise(this.$route.params.workoutName)
        .then((response) => {
          this.detailedExercise = response.data;
          this.isLoading = false;
        })
        .catch((error) => {
          this.isLoading = false;
          console.log(error);
        });
    },
    getAllReviewForExercise(exerciseId) {
      ReviewService.getAllReviewsByExerciseID(exerciseId).then((response) => {
        this.reviews = response.data;
        this.$store.commit("SET_REVIEWS", this.reviews);
        this.isLoading = false;
      })
      .catch((error) => {
          this.isLoading = false;
          console.log(error);
        });
    },
    capitalizeLetter(text) {
      if (!text) {
        return "";
      }
      return text.charAt(0).toUpperCase() + text.slice(1);
    },
    routeToAddReview() {
      this.$router.push({name: 'AddReviewView', params: {exerciseId : this.$route.params.exerciseId}});
    }
  },
  created() {
    const exerciseId = this.$route.params.exerciseId;
    this.exerciseId = exerciseId
    this.getDetailedExercise();
    this.getAllReviewForExercise(exerciseId);
  },
};
</script>

<style scoped>

.exercise-card {
    display: flex;
    flex-direction: column;
    background-color: #a07676bb;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    height: 50%;
    overflow-y: auto;
}

.review-section {
    display: flex;
    flex-direction: column;
    background-color: darkgray;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    height: 50%;
    overflow-y: auto;
}

.review-section h3 {
  display: flex;
  margin-bottom: 5px;
}

.comment-row {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.comment-row button {
  margin-left: auto;
}

#comment-area {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}


</style>