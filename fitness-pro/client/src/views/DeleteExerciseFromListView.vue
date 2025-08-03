<template>
  <div>
    <form v-on:submit.prevent="removeExerciseFromList">
      <h1>Remove Exercise From List</h1>
      <div id="fields">
        <label for="listName">List Name</label>
        <input
          type="text"
          id="listName"
          placeholder="List Name"
          v-model="list.listName"
          readonly
        />
        <label for="workoutName">Exercise Name</label>
        <LoadingSpinner v-if="isLoading" :spin="isLoading"/>
        <select v-model="selectedId" name="listName" id="listName">
          <option v-for="exercise in exercises" :key="exercise.id" :value="exercise.id">
            {{ exercise.workoutName }}
          </option>
        </select>
        <div class="submit-button">
            <button type="submit">Submit</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import LoadingSpinner from '../components/LoadingSpinner.vue';
import UserExerciseService from '../services/UserExerciseService';

export default {
  components: {
    LoadingSpinner
  },
    data() {
        return {
            list: {
                listId: '',
                listName: '',
            },
            exercises: [],
            isLoading: true,
            selectedId: null,
        }
    },
    methods: {
        getListById(listId) {
            UserExerciseService.getListById(listId).then((response) => {
                this.list = response.data
                this.isLoading = false;
            })
            .catch((error) => {
                console.log('Error occurred when getting list by ID:', error)
            })
        },
        getAllExercisesInList(listId) {
          UserExerciseService.getAllExercisesInList(listId).then((response) => {
            this.exercises = response.data
            this.isLoading = false;
          })
          .catch((error) => {
                console.log('Error occurred when getting all exercises in the list:', error)
            })
        },
        removeExerciseFromList() {
          UserExerciseService.removeExerciseFromList(this.$route.params.listId, this.selectedId).then(() => {
            this.$router.push({name: 'UserExerciseListView'});
          })
        },
    },
    created() {
        const listId = this.$route.params.listId;
        this.getListById(listId);
        this.getAllExercisesInList(listId)
    }
}
</script>

<style>

</style>