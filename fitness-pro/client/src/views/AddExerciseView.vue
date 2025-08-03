<template>
  <div>
    <form>
      <h1>Add An Exercise</h1>
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
          <form id="createExerciseButton" v-on:submit.prevent="createExercise">
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
    createExercise() {
      ExerciseService.createExercise(this.exercise)
        .then(() => {
          this.$router.push({ name: "home" });
        })
        .catch((error) => {
          console.log("Error occurred creating an exercise:", error);
        });
    },
  },
};
</script>

<style scoped>

#createExerciseButton {
  display: flex;
  justify-content: center;
}
</style>