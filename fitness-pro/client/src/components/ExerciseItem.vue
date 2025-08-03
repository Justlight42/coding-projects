<template>
  <div class="exercise-card">
    <router-link v-if="showLinks" :to="{name : 'ExerciseView', params: {workoutName: exercise.workoutName, exerciseId: exercise.id}}">
        <h3>Exercise Name: {{ exercise.workoutName }}</h3>
        </router-link>
        <h3 v-else>Exercise Name: {{ exercise.workoutName }}</h3>
        <p>Category: {{ exercise.category }}</p>
        <p>Rating: {{ exercise.rating === 0 ? 'Unrated' : exercise.rating + ' ⭐' }}</p>
        <p>Description: {{ exercise.description }}</p>
        <div class="exercise-button-row">
            <router-link v-if="showLinks && isLoggedIn" :to="({name: 'AddExerciseToListView', params: {exerciseId: exercise.id}})">Add to list</router-link>
        <div v-if="isAdmin && showAdminButtons" class="admin-buttons">
            <router-link :to="({name: 'updateExercise', params: {exerciseId: exercise.id}})">
            <span>✏️</span>
            </router-link>
            <span class="logo-cursor" v-on:click.prevent="deleteExercise">🗑️</span>
        </div>
        </div>
  </div>
</template>

<script>
import ExerciseService from '../services/ExerciseService';

export default {
    props: {
        exercise: {
            type: Object,
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
        showLinks: {
            type: Boolean,
            default: true
        },
    },
    computed: {
        isLoggedIn() {
            return this.$store.state.user.id ? true : false;
        }
    },
    methods: {
        deleteExercise() {
            ExerciseService.deleteExercise(this.exercise.id).then(() => {
                this.$store.commit("REMOVE_EXERCISE", this.exercise);
                alert(`${this.exercise.workoutName} has been successfully deleted`);
            })
            .catch((error) => {
                console.log('Error occurred when deleting an exercise:', error)
            })
        }
    }

}
</script>

<style scoped>

.exercise-card {
    display: flex;
    flex-direction: column;
    background-color: #fff;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    height: 100%;
    background: lightsteelblue;
}

.exercise-card img {
    width: 100%;
    height: 150px;
    object-fit: cover;
    border-radius: 4px;
    margin-bottom: 0.5rem;
}

.exercise-card h3,
.exercise-card p {
    margin: 0.25rem 0;
}

.exercise-button-row {
    display: flex;
    justify-content: right;
    margin-top: auto;
    cursor: pointer;
}

</style>