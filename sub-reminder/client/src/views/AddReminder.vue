<template>
  <div id="createReminder">
    <form v-on:submit.prevent="createReminder">
      <h1>Create A Reminder</h1>
      <div id="fields">
        <label for="subName">Subscription Name</label>
        <select v-model="reminder.subId" id="subName">
            <option v-for="sub in subscriptions" :key="sub.subId" :value="sub.subId">
                {{ sub.subName }}
            </option>
        </select>
        <label for="reminderDate">Reminder Date</label>
        <input
          type="text"
          id="reminderDate"
          placeholder="Select a date"
          :value="formattedDate"
          @click="!autoReminder && (showPicker = true)"
          readonly
        />
        <div v-if="showPicker" class="popup-screen">
            <div class="calender-popup">
                <VueDatePicker v-model="reminder.reminderDate" :enable-time-picker="false" auto-position teleport-center />
                <div class="popup-confirms">
                <button @click="showPicker = false" class="calender-cancel">Cancel</button>
                <button @click="showPicker = false" class="calender-confirm">Confirm</button>
                </div>
            </div>
        </div>
        <label>
            <input type="checkbox" v-model="autoReminder" @change="autoReminder && setAutoReminder()" />
            Set reminders 3 days before billing date
        </label>
        <div class="submit-button">
          <button type="submit">Create Reminder</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import SubscriptionService from '../services/SubscriptionService';
import ReminderService from '../services/ReminderService';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';

export default {
    components: {
        VueDatePicker
    },
    data() {
        return {
            reminder: {
                subId: '',
                reminderDate: '',
                sent: false
            },
            subscriptions: [],
            showPicker: false,
            autoReminder: false
        }
    },
    computed: {
        formattedDate() {
            return this.reminder.reminderDate ? new Date(this.reminder.reminderDate).toLocaleDateString() : ''
        }
    },
    methods: {
        getAllSubsByUserId() {
            SubscriptionService.getAllSubsByUserId(this.$store.state.user.id).then((response) => {
                this.subscriptions = response.data;
            })
            .catch((error) => {
                console.log('Error has occurred getting all subscriptions for a user', error)
            })
        },
        createReminder() {
            const formatDate = new Date(this.reminder.reminderDate);
            this.reminder.reminderDate = `${formatDate.getFullYear()}-${String(formatDate.getMonth() + 1).padStart(2, '0')}-${String(formatDate.getDate()).padStart(2, '0')}`;

            ReminderService.createReminder(this.reminder).then((response) => {
                this.$store.commit('CREATE_REMINDER', response.data)
                this.$router.back();
            })
            .catch((error) => {
                console.log('Error has occurred creating a reminder', error)
            })
        },
        setAutoReminder() {
            const selectedSub = this.subscriptions.find(sub => sub.subId === this.reminder.subId);
            if (selectedSub && selectedSub.nextBilling) {
                const billingDate = new Date(selectedSub.nextBilling);
                const today = new Date();
                const timeDifference = billingDate.getTime() - today.getTime();
                const dayDifference = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));
                if (dayDifference <= 3) {
                    alert('The billing date is too close to use the auto reminder date.');
                    return;
                }
                billingDate.setDate(billingDate.getDate() - 3);
                this.reminder.reminderDate = `${billingDate.getFullYear()}-${String(billingDate.getMonth() + 1).padStart(2, '0')}-${String(billingDate.getDate()).padStart(2, '0')}`;
            }
        },
    },
    created() {
        this.getAllSubsByUserId();
    }
}
</script>

<style>

</style>