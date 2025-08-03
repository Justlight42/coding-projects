<template>
    <div class="exercise-grid">
        <ExerciseItem v-for="exercise in $store.state.exercises" :key="exercise.id" :exercise="exercise" :isAdmin="isAdmin" :showAdminButtons="showAdminButtons"/>
  </div>
</template>

<script>
import ExerciseItem from "./ExerciseItem.vue";

export default {
  components: {
    ExerciseItem
  },
  props: {
    exercises: {
      type: Array,
      required: true
    },
    isAdmin: {
      type: Boolean,
      default: false
    },
    showAdminButtons: {
      type: Boolean,
      default: false
    },
  }
};
</script>

<style scoped>

.exercise-grid {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    gap: 1.5rem;
}

@media only screen and (max-width: 425px) {
  .exercise-grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
}


</style>