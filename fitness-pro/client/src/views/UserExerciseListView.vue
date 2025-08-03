<template>
    <div id="topList">
        <router-link id="addList" :to="({name: 'AddListView'})">Create A New List</router-link>
    </div>
  <div class="list-grid">
    <LoadingSpinner v-if="isLoading" :spin="isLoading" />
    <UserExerciseList v-else v-for="list in $store.state.exerciseLists" :key="list.listId" :list="list" :isAdmin="isAdmin" :showAdminButtons="showAdminButtons"/>
   
  </div>
</template>

<script>
import LoadingSpinner from '../components/LoadingSpinner.vue';
import UserExerciseList from '../components/UserExerciseList.vue';
import UserExerciseService from '../services/UserExerciseService';

export default {
    components: {
        UserExerciseList,
        LoadingSpinner
    },
    data() {
        return {
            lists: [],
            isAdmin: false,
            showAdminButtons: false,
            isLoading: true,
        }
    },
    methods: {
        getListByUser() {
            UserExerciseService.getListByUser(this.$store.state.user.id).then((response) => {
                this.lists = response.data
                this.$store.commit("SET_EXERCISE_LISTS", this.lists)
                this.isLoading = false;
            })
            .catch((error) => {
                console.log('Error occurred when getting the user exercise list:', error);
            })
        },
    },
    created() {
        this.getListByUser();
    }
}
</script>

<style scoped>

.list-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

#topList {
    display: flex;
    margin: 10px;
}

#addList {
    margin-left: auto;
}

@media only screen and (max-width: 425px) {

.list-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

}





</style>