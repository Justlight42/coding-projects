<template>
  <div>
    <SubscriptionList :subscriptions="$store.state.subscriptions" />
  </div>
</template>

<script>
import SubscriptionList from '../components/SubscriptionList.vue';
import SubscriptionService from '../services/SubscriptionService';

export default {
    components: {
        SubscriptionList
    },
    data() {
        return {
            
        }
    },
    methods: {
        getAllSubsByUserId() {
            const userId = this.$store.state.user.id
            SubscriptionService.getAllSubsByUserId(userId).then((response) => {
                this.$store.commit('SET_SUBSCRIPTIONS', response.data);
            })
            .catch((error) => {
                console.log('Failed to get all subscriptions:', error)
            })
        }
    },
    created() {
        this.getAllSubsByUserId();
    }
}
</script>

<style>

</style>