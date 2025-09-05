import axios from 'axios';

const BASE_REMINDER_PATH = 'api/reminder'

export default {

    getReminderById(reminderId) {
        return axios.get(`${BASE_REMINDER_PATH}/${reminderId}`);
    },

    getAllReminders(userId) {
        return axios.get(`${BASE_REMINDER_PATH}/list/${userId}`);
    },

    createReminder(reminder) {
        return axios.post(`${BASE_REMINDER_PATH}`, reminder);
    },

    updateReminder(reminder, reminderId) {
        return axios.put(`${BASE_REMINDER_PATH}/${reminderId}`, reminder);
    },

    deleteReminder(reminderId) {
        return axios.delete(`${BASE_REMINDER_PATH}/${reminderId}`);
    },

    getNotifications() {
        return axios.get(`${BASE_REMINDER_PATH}`);
    },

}