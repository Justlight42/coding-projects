<template>
  <div>
    <form v-on:submit.prevent="createList">
      <h1>Create A New List</h1>
      <div id="fields">
        <label for="listName">List Name</label>
        <input
          type="text"
          id="listName"
          placeholder="Enter a name for list"
          v-model="list.listName"
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
import UserExerciseService from '../services/UserExerciseService';

export default {
  data() {
    return {
      list: {
        listName: ''
      },
    }
  },
  methods: {
    createList() {
      UserExerciseService.createList(this.list).then(() => {
        this.$router.push({name: 'UserExerciseListView'});
      })
      .catch((error) => {
        console.log('Error occurred when trying to create a new list:', error)
      })
    }
  }
}
</script>

<style>

</style>