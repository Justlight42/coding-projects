<template>
  <div class="reminder-card">
    <div class="reminder-topCard">
      <div class="reminder-topInfo">
        <h2>{{ reminder.subName }}</h2>
        <div class="reminder-buttons">
          <router-link :to="{ name: 'UpdateReminder', params: {reminderId: reminder.reminderId} }">✏️</router-link>
          <span @click="deleteReminder" id="delete-reminder">🗑️</span>
        </div>
      </div>
      <p>Date: {{ formatDate() }}</p>
    </div>
  </div>
</template>

<script>
import ReminderService from '../services/ReminderService';

export default {
  props: {
    reminder: Object
  },
  methods: {
    formatDate() {
      const reminderDate = new Date(this.reminder.reminderDate.replace(/-/g, '/')) // replaces all (-) with (/) which formats date to UTC.
      return reminderDate.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
    },
    deleteReminder() {
      ReminderService.deleteReminder(this.reminder.reminderId).then(() => {
        this.$store.commit('DELETE_REMINDER', this.reminder.reminderId)
        alert(`You have successfully deleted ${this.reminder.subName} reminder`)
      })
      .catch((error) => {
        console.log('Error has occurred deleting reminder', error)
      })
    }
  }

}
</script>

<style scoped>

.reminder-topInfo {
  display: flex;
  justify-content: space-between;
}

#delete-reminder {
  cursor: pointer;
}

.reminder-topCard h2,
.reminder-topCard span {
  font-size: 1.3rem;
}

.reminder-topCard p {
  font-size: 1.1rem;
}

</style>