<template>
  <div class="sub-list">
    <SubscriptionCard v-for="sub in subscriptions" :key="sub.subId" :sub="sub"/>
  </div>
</template>

<script>
import SubscriptionCard from './SubscriptionCard.vue';

export default {
    props: {
        subscriptions: Array,
    },
    components: {
        SubscriptionCard
    },
}
</script>

<style>

</style>