<template>
  <div class="session-list">
    <SessionCard v-for="session in $store.state.sessions" :key="session.sessionId" :session="session"/>
  </div>
</template>

<script>
import SessionCard from './SessionCard.vue';

export default {
    components: {
        SessionCard,
    },
}
</script>

<style>

</style>