<template>
  <div class="sub-card">
    <div class="sub-topCard">
      <h2>{{ sub.subName }}</h2>
      <div>
      <router-link v-bind:to="{name: 'UpdateSubscription', params: {subId: sub.subId}}">
        ✏️
      </router-link>
      <span @click="deleteSub" id="deleteSub">🗑️</span>
      </div>
    </div>
    <p>Cost: ${{ sub.cost }}</p>
    <p>Refreshes: {{ sub.billingCycle }}</p>
    <p>Payment Due: {{ formatDate() }}</p>
    <a :href="sub.siteURL" target="_blank" rel="noopener noreferrer">{{ sub.siteURL }}</a>
  </div>
</template>

<script>
import SubscriptionService from '../services/SubscriptionService';
import ReminderService from '../services/ReminderService';

export default {
    props: {
        sub: Object
    },
    methods: {
      deleteSub() {
        SubscriptionService.deleteSub(this.sub.subId).then(() => {
          this.$store.commit('DELETE_SUBSCRIPTION', this.sub.subId)
          this.refreshReminders();
          alert(`You have successfully deleted ${this.sub.subName}`)
        })
        .catch((error) => {
          console.log('Error has occurred deleting subscription', error)
        })
      },
      formatDate() {
        const billingDate = new Date(this.sub.nextBilling.replace(/-/g, '/')) // replaces all (-) with (/) which formats date to UTC.
        return billingDate.toLocaleDateString('en-US', {year: 'numeric', month: 'short', day: 'numeric'});
      },
      refreshReminders() {
        ReminderService.getAllReminders(this.$store.state.user.id).then((response) => {
          this.$store.commit('SET_REMINDERS', response.data)
          this.isLoading = false;
        })
          .catch((error) => {
            console.log('Error has occurred getting all reminders', error)
          })
        }
    }
}
</script>

<style scoped>

.sub-card {
  border: 3px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  height: 200px;
  overflow-y: auto;
}

.sub-topCard {
  display: flex;
  justify-content: space-between;
}

.sub-card h2 {
  font-size: 1.5rem;
}

.sub-card p,
.sub-card a,
.sub-card span {
  font-size: 1.2rem;
}

#deleteSub {
  cursor: pointer;
}
/* Hold for delete 🗑️ */
</style>