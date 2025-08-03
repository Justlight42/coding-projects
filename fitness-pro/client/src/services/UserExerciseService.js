import axios from 'axios';

const BASE_EXERCISE_LIST_PATH = 'api/userExiList'

export default {

    getAllExercisesInList(listId) {
        return axios.get(`${BASE_EXERCISE_LIST_PATH}/exerciseList/${listId}`);
    },
    getListByUser(userId) {
        return axios.get(`${BASE_EXERCISE_LIST_PATH}/userList/${userId}`);
    },
    getListById(listId) {
        return axios.get(`${BASE_EXERCISE_LIST_PATH}/list/${listId}`)
    },
    getListsByExerciseId(exerciseId) {
        return axios.get(`${BASE_EXERCISE_LIST_PATH}/lists/${exerciseId}`)
    },
    createList(userExerciseList) {
        return axios.post(`${BASE_EXERCISE_LIST_PATH}/list`, userExerciseList);
    },
    addExerciseToList(exerciseListEntry) {
        return axios.post(`${BASE_EXERCISE_LIST_PATH}/list/addExercise`, exerciseListEntry);
    },
    updateListName(listId, userExerciseList) {
        return axios.put(`${BASE_EXERCISE_LIST_PATH}/list/${listId}`, userExerciseList)
    },
    removeExerciseFromList(listId, exerciseId) {
        return axios.delete(`${BASE_EXERCISE_LIST_PATH}/list/${listId}/deleteExercise/${exerciseId}`)
    },
    deleteList(listId) {
        return axios.delete(`${BASE_EXERCISE_LIST_PATH}/list/${listId}`)
    }

}