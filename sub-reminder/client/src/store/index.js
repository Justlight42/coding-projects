import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      subscriptions: [],
      reminders: [],
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
      SET_SUBSCRIPTIONS(state, subs) {
        state.subscriptions = subs;
      },
      CREATE_SUBSCRIPTION(state, subs) {
        state.subscriptions.push(subs);
      },
      DELETE_SUBSCRIPTION(state, subId) {
        state.subscriptions = state.subscriptions.filter(sub => sub.subId != subId)
      },
      SET_REMINDERS(state, reminders) {
        state.reminders = reminders;
      },
      CREATE_REMINDER(state, reminder) {
        state.reminders.push(reminder);
      },
      DELETE_REMINDER(state, reminderId) {
        state.reminders = state.reminders.filter(re => re.reminderId != reminderId);
      },

    },

  })
  return store;
}