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
    <p>Cost ${{ sub.cost }}</p>
    <p>Refreshes {{ sub.billingCycle }}</p>
    <p>Next Payment Due {{ formatDate() }}</p>
    <a :href="sub.siteURL" target="_blank" rel="noopener noreferrer">{{ sub.siteURL }}</a>
  </div>
</template>

<script>
import SubscriptionService from '../services/SubscriptionService';

export default {
    props: {
        sub: Object
    },
    methods: {
      deleteSub() {
        SubscriptionService.deleteSub(this.sub.subId).then(() => {
          this.$store.commit('DELETE_SUBSCRIPTION', this.sub.subId)
          alert(`You have successfully deleted ${this.sub.subName}`)
        })
        .catch((error) => {
          console.log('Error has occurred deleting subscription', error)
        })
      },
      formatDate() {
        const billingDate = new Date(this.sub.nextBilling.replace(/-/g, '/')) // replaces all (-) with (/) which formats date to UTC.
        return billingDate.toLocaleDateString('en-US', {year: 'numeric', month: 'short', day: 'numeric'});
      }
    }
}
</script>

<style scoped>

.sub-topCard {
  display: flex;
  justify-content: space-between;
}

#deleteSub {
  cursor: pointer;
}
/* Hold for delete 🗑️ */
</style>