<template>
  <div id="updateSub">
    <form v-on:submit.prevent="updateSub">
      <h1>Update Subscription</h1>
      <div id="fields">
        <label for="subName">Subscription Name</label>
        <input
          type="text"
          id="subName"
          placeholder="Subscription Name"
          v-model="subscription.subName"
          required
        />
        <label for="cost">Cost</label>
        <input
          type="number"
          id="cost"
          placeholder="Cost Of Subscription"
          v-model.number="subscription.cost"
          step="0.01"
          required
        />
        <label for="billingCycle">Billing Cycle</label>
        <select v-model="subscription.billingCycle" id="billingCycle">
            <option value="monthly">Monthly</option>
            <option value="yearly">Yearly</option>
        </select>
        <label for="nextBilling">Next Payment Due</label>
        <input
          type="text"
          id="nextBilling"
          placeholder="Select a date"
          :value="formattedDate"
          @click="showPicker = true"
          readonly
        />
        <div v-if="showPicker" class="popup-screen">
            <div class="calender-popup">
                <VueDatePicker v-model="subscription.nextBilling" :enable-time-picker="false" auto-position teleport-center />
                <div class="popup-confirms">
                <button @click="showPicker = false" class="calender-cancel">Cancel</button>
                <button @click="showPicker = false" class="calender-confirm">Confirm</button>
                </div>
            </div>
        </div>
        <label for="siteURL">Website</label>
        <input
          type="text"
          id="siteURL"
          placeholder="Website"
          v-model="subscription.siteURL"
          required
        />
        <div class="submit-button">
          <button type="submit">Update Subscription</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import SubscriptionService from '../services/SubscriptionService';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

export default {
    components: {
        VueDatePicker
    },
    data() {
        return {
            subscription: {
                subId: this.$route.params.subId,
                userId: this.$store.state.user.id,
                subName: '',
                cost: '',
                billingCycle: '',
                nextBilling: '',
                siteURL: ''
            },
            showPicker: false,
        }
    },
    computed: {
        formattedDate() {
            return this.subscription.nextBilling ? new Date(this.subscription.nextBilling).toLocaleDateString() : ''
        }
    },
    methods: {
        updateSub() {
          const formatDate = new Date(this.subscription.nextBilling);
          //padStart ensures that date will always start with two digits
          this.subscription.nextBilling = `${formatDate.getFullYear()}-${String(formatDate.getMonth() + 1).padStart(2, '0')}-${String(formatDate.getDate()).padStart(2, '0')}`;
            SubscriptionService.updateSub(this.subscription, this.subscription.subId).then(() => {
                this.$router.back();
            })
            .catch((error) => {
                console.log('Error has occurred when updating the subscription', error);
            }) 
        },
        getSubById() {
            const subId = this.$route.params.subId;
            SubscriptionService.getSubById(subId).then((response) => {
                this.subscription = response.data;
            })
        },
    },
    created() {
        this.getSubById();
    }
}
</script>

<style scoped>


</style>