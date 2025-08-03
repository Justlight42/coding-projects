<template>
  <div>
    <form v-on:submit.prevent="updateListName">
      <h1>Update An List's Name</h1>
      <div id="fields">
        <label for="listName">List Name</label>
        <input
          type="text"
          id="listName"
          placeholder="Workout Name"
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
                listName: '',
            }
        }
    },
    methods: {
      getListById(listId) {
        UserExerciseService.getListById(listId).then((response) => {
          this.list = response.data
        })
      },
      updateListName() {
        UserExerciseService.updateListName(this.list.listId, this.list).then(() => {
          this.$router.push({name: 'UserExerciseListView'});
        })
        .catch((error) => {
          console.log('Error occurred when updating the list name:', error);
        })
      }
    },
    created() {
      let listId = this.$route.params.listId;
      this.getListById(listId);

    },
}
</script>

<style>

</style>