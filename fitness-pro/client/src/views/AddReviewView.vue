<template>
  <div>
    <form v-on:submit.prevent="createReview">
      <h1>Add a comment</h1>
      <LoadingSpinner v-if="isLoading" :spin="isLoading"/>
      <div id="fields" v-else>
        <h3>{{ exercise.workoutName }}</h3>
        <label for="rating">Rating</label>
        <input
          type="number"
          id="rating"
          placeholder="Rate 1-5"
          v-model="review.rating"
          required
        />
        <label for="comment">Comment</label>
        <input
          type="text"
          id="comment"
          placeholder="Comment"
          v-model="review.comment"
          required
        />
        <div class="submit-button">
            <button type="submit">Submit</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import ExerciseService from '../services/ExerciseService';
import ReviewService from '../services/ReviewService';
import LoadingSpinner from '../components/LoadingSpinner.vue';

export default {
    components: {
        LoadingSpinner
    },
    data() {
        return {
            exercise: null,
            isLoading: true,
            review: {
                exerciseId: null,
                userId: null,
                rating: "",
                description: "",
            },
        };
    },
    methods: {
        createReview() {
            ReviewService.createReview(this.review).then(() => {
                this.$router.push({name : 'ExerciseView', params: {workoutName: this.exercise.workoutName, exerciseId: this.exercise.id}})
            })
                .catch((error) => {
                    console.log('Error occurred when trying to create a comment:', error)
                })
        },
        getExerciseById(exerciseId) {
            ExerciseService.getExerciseById(exerciseId).then((response) => {
                this.exercise = response.data;
                this.isLoading = false;
            })
                .catch((error) => {
                    console.log("Error occurred when getting exercise by Id:", error);
                });
        }

    },
  created() {
    const exerciseId = this.$route.params.exerciseId;
    this.review.exerciseId = this.$route.params.exerciseId;
    this.review.userId = this.$store.state.user.id;
    this.getExerciseById(exerciseId);
  }
}
</script>

<style scoped>

#fields h3 {
    font-size: larger;
    margin-bottom: 5px;
}

</style>