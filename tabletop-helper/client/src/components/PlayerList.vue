<template>
    <div class="player-list">
        <PlayerCard v-for="player in players" :key="player.playerId" :playerId="player.playerId" />
    </div>
</template>

<script>
import PlayerCard from './PlayerCard.vue'

export default {
    components: {
        PlayerCard,
    },
    computed: {
        players() {
            return this.$store.state.players
        }
    },
}

</script>

<style>

</style>