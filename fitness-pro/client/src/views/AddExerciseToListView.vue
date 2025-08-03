<template>
  <div>
    <form v-on:submit.prevent="addExerciseToList">
      <h1>Add Exercise To List</h1>
      <div id="fields">
        <label for="workoutName">Exercise Name</label>
        <input
          type="text"
          id="workoutName"
          placeholder="Exercise Name"
          v-model="exercise.workoutName"
          readonly
        />
        <label for="listName">List Name</label>
        <select v-model="listEntry.listId" name="listName" id="listName">
          <option v-for="list in filteredLists" :key="list.listId" :value="list.listId">
            {{ list.listName }}
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
import UserExerciseService from '../services/UserExerciseService';
import ExerciseService from '../services/ExerciseService';

export default {
  data() {
    return {
      exercise: {
        workoutName: '',
        exerciseId: ''
      },
      listEntry: {
        exerciseId: this.$route.params.exerciseId,
        listId: '',
      },
      exerciseList: [],
      filteredLists: []
    }
  },
  methods: {
    getExerciseById(exerciseId) {
      ExerciseService.getExerciseById(exerciseId).then((response) => {
        this.exercise = response.data
        this.listEntry.exerciseId = response.data.id;
      })
      .catch((error) => {
        console.log('Error occurred getting the exercise by id:', error);
      })
    },
    getListByUser() {
            UserExerciseService.getListByUser(this.$store.state.user.id).then((response) => {
                this.$store.commit("SET_EXERCISE_LISTS", response.data)
            })
            .catch((error) => {
                console.log('Error occurred when getting the user exercise list:', error);
            })
        },
    addExerciseToList() {
      UserExerciseService.addExerciseToList(this.listEntry).then(() => {
        this.$router.push({name: 'home'});
      })
    },
    getListByExerciseId() {
      UserExerciseService.getListsByExerciseId(this.$route.params.exerciseId).then((response) => {
        this.exerciseList = response.data;
        const exerciseId = this.$route.params.exerciseId;
        this.filteredLists = this.filteredList(exerciseId);
      })
      .catch((error) => {
          console.log('Error occurred when getting the list by exercise Id:', error);
      })
    },
    filteredList() {
      const userList = this.$store.state.exerciseLists;
      const exerciseList = this.exerciseList;

      const matchedListId = exerciseList.map(entry => entry.listId)
      return userList.filter((list) => !matchedListId.includes(list.listId))
    }
  },
  created() {
    this.getListByUser();
    this.getListByExerciseId();
    const exerciseId = this.$route.params.exerciseId;
    this.getExerciseById(exerciseId);
  }
}
</script>

<style>

</style>