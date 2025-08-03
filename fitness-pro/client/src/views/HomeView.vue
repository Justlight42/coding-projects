<template>
  <div class="home-view">
    <div class="filterArea">
      <div class="filter-row">
        <label for="filterSelect">Filter: </label>
        <select id="filter-select" v-model="filter">
          <option value="category">Search by category</option>
          <option value="workoutName">Search by workout name</option>
        </select>
      </div>
      <input id="filter-input" v-model="search" placeholder="Enter in the criteria" />
      <div class="filter-confirms">
        <button class="filter-button" v-on:click.prevent="applyFilter">Apply</button>
        <button class="filter-button" v-on:click.prevent="resetFilter">Reset</button>
      </div>
      <div id="admin-button-addExercise">
        <router-link v-if="isAdmin" :to="({name: 'addExercise'})">
          <button>Add Exercise</button>
        </router-link>
      </div>
    </div>
    <LoadingSpinner v-if="isLoading" :spin="isLoading" />
    <ExerciseList v-else :exercises="$store.state.exercises" :isAdmin="isAdmin" :showAdminButtons="showAdminButtons" />

  </div>
</template>

<script>
import ExerciseList from "../components/ExerciseList.vue";
import ExerciseService from "../services/ExerciseService";
import LoadingSpinner from "../components/LoadingSpinner.vue";

export default {
  components: {
    ExerciseList,
    LoadingSpinner
  },
  data() {
    return {
      exercises: [], // This holds exercises that get filtered
      filter: null,
      search: '',
      isLoading: true,
      isAdmin: false,
      showAdminButtons: true
    }
  },
  methods: {
    getAllExercises() {
      ExerciseService.getAllExercises().then((response) => {
        this.exercises = response.data;
        this.$store.commit("SET_EXERCISES", this.exercises)
        this.isLoading = false;
      })
      .catch((error) => {
        this.isLoading = false;
        console.log('Error occurred when loading all exercises', error);
      })
    },
    applyFilter() {
      if (this.filter === 'category') {
        ExerciseService.getExercisesByCategory(this.search).then((response) => {
          this.$store.commit("SET_EXERCISES", response.data)
        })
        .catch((error) => {
        console.log('Error occurred when filtering exercises by category:', error)
        })
      } else if (this.filter === 'workoutName') {
        ExerciseService.getFilteredExerciseByWorkoutName(this.search).then((response) => {
          this.$store.commit("SET_EXERCISES", response.data)
        })
        .catch((error) => {
        console.log('Error occurred when filtering exercises by workout name:', error)
        })
      }
    },
    resetFilter() {
      this.filter = '';
      this.search = '';
      this.getAllExercises()
    },
    checkIfAdmin() {
      return this.$store.state.user.role === 'ROLE_ADMIN';
    }
  },
  created() {
    this.getAllExercises();
    this.isAdmin = this.checkIfAdmin();
  },
};
</script>

<style scoped>

#admin-button-addExercise {
  margin-left: auto;
  margin-right: 1rem;
}

@media only screen and (max-width: 425px) {
  #admin-button-addExercise {
    margin:auto;
    margin-bottom: 5px;
  }
}

</style>