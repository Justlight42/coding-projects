<template>
  <div class="player-card">
    <h2>{{ player.name }}</h2>
    <p v-if="player.teamName != null">Team Name: {{ player.teamName }}</p>
    <p v-if="player.health != null">Health: {{ player.health }}</p>
    <p v-if="player.score != null">Score: {{ player.score }}</p>
  </div>
</template>

<script>
export default {
    props: {
        player: {
            type: Object,
            required: true
        }
    }
}
</script>

<style>

</style>