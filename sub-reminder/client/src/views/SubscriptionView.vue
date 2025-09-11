<template>
  <div class="sub-view">
    <div class="sub-topView">
        <input type="text" v-model="searchResult" placeholder="Search for subscription">
        <div class="sub-links">
            <router-link  :to="{name: 'AddReminder'}">Add Reminder</router-link>
            <router-link  :to="{name: 'AddSubscription'}">Add Subscription</router-link>
        </div>
    </div>
    <div class="sub-infoSection">
        <loading-spinner v-if="isLoading" :spin="isLoading" />
        <div id="sub-userList">
            <SubscriptionList :subscriptions="filterSubscription()" />
        </div>
        <div id="sub-userReminder">
            <h2 id="reminder-text">Reminders</h2>
            <hr />
            <ReminderItem id="sub-reminderInfo" v-for="reminder in this.$store.state.reminders" :key="reminder.reminderId" :reminder="reminder" />
        </div>
    </div>
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

.sub-view {
    height: 95%;
}

.sub-topView {
    display: flex;
    padding: 2px;
    align-content: center;
    height: 40px;
    grid-area: topBar;
}

.sub-links {
    display: flex;
    align-items: center;
    height: auto;
    margin-left: auto;
    gap: 10px;
}

.sub-infoSection {
    display: grid;
    grid-template-columns: 1fr 0.3fr;
    grid-template-areas: "subLeft subRight";
    column-gap: 10px;
    height: 100%;
}

#sub-userList {
    grid-area: subLeft;
}

#sub-userReminder {
    grid-area: subRight;
    border: 3px solid #ddd;
    padding: 6px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    box-sizing: border-box;
    border-radius: 8px;
    height: 100%;
}

#reminder-text {
    display: flex;
    justify-content: center;
    font-size: 20px;
    padding: 5px;
}

#sub-reminderInfo {
    margin-left: 10px;
    padding: 5px;
}

#sub-userReminder h2 {
    font-size: 1.5rem;
}

</style>