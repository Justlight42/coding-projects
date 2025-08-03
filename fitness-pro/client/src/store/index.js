import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      exercises: [],
      reviews: [],
      exerciseLists: [],
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
      SET_EXERCISES(state, exercises) {
        state.exercises = exercises;
      },
      REMOVE_EXERCISE(state, exercise) {
        state.exercises = state.exercises.filter((ex) => {
          return ex.id != exercise.id
        })
      },
      SET_REVIEWS(state, reviews) {
        state.reviews = reviews;
      },
      REMOVE_REVIEW(state, review) {
        state.reviews = state.reviews.filter((re) => {
          return re.reviewId != review.reviewId;
        })
      },
      SET_EXERCISE_LISTS(state, userExerciseList) {
        state.exerciseLists = userExerciseList;
      },
      REMOVE_EXERCISE_LIST(state, userExerciseList) {
        state.exerciseLists = state.exerciseLists.filter((usExi) => {
          return usExi.listId != userExerciseList.listId;
        })
      }
    },

  })
  return store;
}