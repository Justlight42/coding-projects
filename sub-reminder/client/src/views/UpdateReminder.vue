<template>
  <div id="updateReminder">
    <form v-on:submit.prevent="updateReminder">
      <h1>Update A Reminder</h1>
      <div id="fields">
        <label for="subName">Subscription Name</label>
        <input 
          type="text"
          id="subName"
          v-model="reminder.subName"
          :key="reminder.subName"
          readonly
          />
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
          <button type="submit">Update Reminder</button>
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
                reminderId: this.$route.params.reminderId,
                subId: '',
                subName: '',
                reminderDate: '',
                sent: ''
            },
            sub: {},
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
        getReminderById() {
            const reminderId = this.$route.params.reminderId;
            ReminderService.getReminderById(reminderId).then((response) => {
                this.reminder.subId = response.data.subId;
                this.reminder.reminderDate = new Date(response.data.reminderDate.replace(/-/g, '/'));
                this.reminder.sent = response.data.sent;
                this.getSubById(this.reminder.subId);
            })
            .catch((error) => {
                console.log('Error has occurred getting the reminder by ID', error)
            })
        },
        getSubById(subId) {
            if (!subId) {
                return //Makes sure getReminders can load first
            }
            SubscriptionService.getSubById(subId).then((response) => {
                this.sub = response.data;
                this.reminder.subName = response.data.subName;
            })
            .catch((error) => {
                console.log('Error has occurred getting the subscription by ID', error)
            })
        },
        updateReminder() {
            const formatDate = new Date(this.reminder.reminderDate);
            this.reminder.reminderDate = `${formatDate.getFullYear()}-${String(formatDate.getMonth() + 1).padStart(2, '0')}-${String(formatDate.getDate()).padStart(2, '0')}`;

            ReminderService.updateReminder(this.reminder, this.reminder.reminderId).then((response) => {
                this.$router.back();
            })
            .catch((error) => {
                console.log('Error has occurred updating a reminder', error)
            })
        },
        setAutoReminder() {
            const sub = this.sub;
            if (sub && sub.nextBilling) {
                const billingDate = new Date(sub.nextBilling.replace(/-/g, '/'));
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
        this.getReminderById();
    }
}
</script>

<style>

</style>