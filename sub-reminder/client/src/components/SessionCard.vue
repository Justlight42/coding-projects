<template>
  <div class="session-card">
    <h3>Game Mode: {{ session.gameMode }}</h3>
    <p>Created By: {{ session.createdById }}</p>
    <p>Session Start: {{ session.startTime }}</p>
    <p>Session End: {{ session.endTime }}</p>
    <p>Session Duration: {{ session.sessionDuration }}</p>
  </div>
</template>

<script>
export default {
    props: {
        session: {
            type: Object,
            required: true
        }
    },
}
</script>

<style>

</style>