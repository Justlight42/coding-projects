<template>
  <div class="session-list">
    <SessionCard v-for="session in sessions" :key="session.sessionId" :session="session"/>
  </div>
</template>

<script>
import SessionCard from './SessionCard.vue';

export default {
    components: {
        SessionCard,
    },
    props: {
      sessions: {
        type: Array,
        required: true
      }
    }
}
</script>

<style>

</style>