import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      playerActions: [],
      players: [],
      sessions: [],
      timer: {
        hours: 0,
        minutes: 0,
        seconds: 0
      },
      timerIsOn: false,
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
      SET_HOURS(state, input) {
        state.timer.hours = input;
      },
      SET_MINUTES(state, input) {
        state.timer.minutes = input;
      },
      SET_SECONDS(state, input) {
        state.timer.seconds = input;
      },
      SET_TIMER_IS_ON(state, input) {
        state.timerIsOn = input
      },
      RESET_TIMER(state) {
        state.timer.hours = 0;
        state.timer.minutes = 0;
        state.timer.seconds = 0;
        state.timerIsOn = false;
      },
      SET_PLAYERS(state, players) {
        state.players = players;
      },

    },

  })
  return store;
}