<template>
  <div class="sub-view">
    <div class="sub-topView">
        <input type="text" v-model="searchResult" placeholder="Search for subscription">
        <div class="sub-links">
            <router-link  :to="{name: 'AddReminder'}">Add Reminder</router-link>
            <router-link  :to="{name: 'AddSubscription'}">Add Subscription</router-link>
        </div>
    </div>
    <hr />
    <loading-spinner v-if="isLoading" :spin="isLoading" />
    <SubscriptionList v-else :subscriptions="filterSubscription()" />
    <ReminderItem v-for="reminder in this.$store.state.reminders" :key="reminder.reminderId" :reminder="reminder" />
  </div>
</template>

<script>
import LoadingSpinner from '../components/LoadingSpinner.vue';
import SubscriptionList from '../components/SubscriptionList.vue';
import ReminderItem from '../components/ReminderItem.vue';
import ReminderService from '../services/ReminderService';
import SubscriptionService from '../services/SubscriptionService';

export default {
    components: {
        SubscriptionList,
        ReminderItem,
        LoadingSpinner,
    },
    data() {
        return {
            searchResult: '',
            isLoading: true
        }
    },
    methods: {
        getAllSubsByUserId() {
            const userId = this.$store.state.user.id
            SubscriptionService.getAllSubsByUserId(userId).then((response) => {
                this.$store.commit('SET_SUBSCRIPTIONS', response.data);
                this.isLoading = false;
            })
            .catch((error) => {
                console.log('Failed to get all subscriptions:', error)
            })
        },
        filterSubscription() {
            const search = this.searchResult ? this.searchResult.toLowerCase() : '';
            return this.$store.state.subscriptions.filter(sub => sub.subName.toLowerCase().includes(search))
        },
        getAllReminders() {
            ReminderService.getAllReminders(this.$store.state.user.id).then((response) => {
                this.$store.commit('SET_REMINDERS', response.data)
                this.isLoading = false;
            })
            .catch((error) => {
                console.log('Error has occurred getting all reminders', error)
            })
        },
    },
    created() {
        this.getAllSubsByUserId();
        this.getAllReminders();
    }
}
</script>

<style scoped>

.sub-topView {
    display: flex;
    padding: 2px;
    align-content: center;
    height: 40px;
}

.sub-links {
    display: flex;
    align-items: center;
    height: auto;
    margin-left: auto;
    gap: 10px;
}

</style>