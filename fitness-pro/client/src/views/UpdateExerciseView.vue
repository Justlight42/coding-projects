<template>
  <div>
    <form>
      <h1>Update An Exercise</h1>
      <div id="fields">
        <label for="workoutName">Workout Name</label>
        <input
          type="text"
          id="workoutName"
          placeholder="Workout Name"
          v-model="exercise.workoutName"
          required
        />
        <label for="category">Category</label>
        <input
          type="text"
          id="category"
          placeholder="Category"
          v-model="exercise.category"
          required
        />
        <label for="description">Description</label>
        <input
          type="text"
          id="description"
          placeholder="Description"
          v-model="exercise.description"
          required
        />
        <div>
          <form id="updateExerciseButton" v-on:submit.prevent="updateExercise">
            <button type="submit">Submit</button>
          </form>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import ExerciseService from "../services/ExerciseService";

export default {
  data() {
    return {
      exercise: {
        workoutName: "",
        category: "",
        description: "",
      },
    };
  },
  methods: {
    getExerciseById(id) {
      ExerciseService.getExerciseById(id)
        .then((response) => {
          this.exercise = response.data;
        })
        .catch((error) => {
          console.log("Error occurred when getting exercise by Id:", error);
        });
    },
    updateExercise() {
      let exerciseId = this.$route.params.exerciseId;
      ExerciseService.updateExercise(exerciseId, this.exercise)
        .then(() => {
          this.$router.push({ name: "home" });
        })
        .catch((error) => {
          console.log("Error occurred when updating the exercise:", error);
        });
    },
  },
  created() {
    let id = this.$route.params.exerciseId;
    this.getExerciseById(id);
  },
};
</script>

<style scoped>

#updateExerciseButton {
  display: flex;
  justify-content: center;
}

</style>