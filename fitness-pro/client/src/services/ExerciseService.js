import axios from 'axios';

const BASE_EXERCISE_PATH = 'api/exercise'

export default {

    getAllExercises() {
        return axios.get(BASE_EXERCISE_PATH)
    },
    getExerciseById(exerciseId) {
        return axios.get(`${BASE_EXERCISE_PATH}/${exerciseId}`)
    },
    getExercisesByCategory(category) {
         return axios.get(`${BASE_EXERCISE_PATH}/category/${category}`)
    },
    getExerciseByWorkoutName(workoutName) {
        return axios.get(`${BASE_EXERCISE_PATH}/workoutName/${workoutName}`)
    },
    getFilteredExerciseByWorkoutName(workoutName) {
        return axios.get(`${BASE_EXERCISE_PATH}/filter/${workoutName}`)
    },
    getDetailedExercise(workoutName) {
        return axios.get(`${BASE_EXERCISE_PATH}/${workoutName.toLowerCase()}/details`)
    },
    createExercise(exercise) {
        return axios.post(`${BASE_EXERCISE_PATH}`, exercise)
    },
    updateExercise(exerciseId, exercise) {
        return axios.put(`${BASE_EXERCISE_PATH}/${exerciseId}`, exercise)
    },
    deleteExercise(exerciseId) {
        return axios.delete(`${BASE_EXERCISE_PATH}/${exerciseId}`)
    }

}