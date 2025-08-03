<template>
    <div class="userExercise-grid">
        <div class="listArea">
            <h3>{{ list.listName }}</h3>
            <div class="listButtons">
                <router-link :to="({name: 'DeleteExerciseFromListView', params: ({listId: list.listId})})">Remove A Exercise</router-link>
                <router-link :to="({name: 'UpdateExerciseListView', params: {listId: list.listId}})">
                <span>✏️</span>
                </router-link>
                <span class="logo-cursor" v-on:click.prevent="deleteList">🗑️</span>
                <span class="logo-cursor" v-on:click="expandList">{{ showExercisesInList ? 'Close' : '☰' }}</span>
            </div>
        </div>
        <div class="exerciseListArea" v-if="showExercisesInList">
            <LoadingSpinner v-if="isLoading" :spin="isLoading" />
            <ExerciseItem v-for="exercise in exercises" :key="exercise.id" :exercise="exercise" :isAdmin="isAdmin" :showAdminButtons="showAdminButtons" :showLinks="showLinks"/>
        </div>
    </div>
</template>

<script>
import UserExerciseService from '../services/UserExerciseService';
import ExerciseItem from './ExerciseItem.vue';
import LoadingSpinner from './LoadingSpinner.vue';

export default {
    components: {
        ExerciseItem,
        LoadingSpinner
    },
    props: {
        isAdmin: {
            type: Boolean,
            default: false
        },
        showAdminButtons: {
            type: Boolean,
            default: false
        },
        list: {
            type: Object,
            required: true
        },
        showLinks: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            exercises: [],
            isLoading: false,
            showExercisesInList: false
        }
    },
    methods: {
        deleteList() {
            UserExerciseService.deleteList(this.list.listId).then(() => {
                this.$store.commit('REMOVE_EXERCISE_LIST', this.list)
                alert(`${this.list.listName} has been successfully deleted`);
            })
            .catch((error) => {
                console.log('Error occurred when deleting the list:', error)
            })
        },
        expandList() {
            if (this.showExercisesInList === false) {
                this.showExercisesInList = true;
                this.getAllExercisesInList();
            }
            else {
                this.showExercisesInList = false;
            }
        },
        getAllExercisesInList() {
            UserExerciseService.getAllExercisesInList(this.list.listId).then((response) => {
                this.exercises = response.data;
                this.isLoading = false;
            })
                .catch((error) => {
                    console.log('Error occurred when getting the exercises in user exercise list:', error);
                })
        }
    },

}
</script>

<style scoped>
 
.userExercise-grid {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: .1fr 1fr;
}

.listArea {
    display: flex;
    background-color: #1fad1c;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    height: 100%;
}

.listButtons {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-left: auto;
}

.exerciseListArea {
    overflow-y: auto;
}

.exercise-card {
    height: auto;
}

.listButtons * {
    display: inline-flex;
    align-items: center;
}

</style>