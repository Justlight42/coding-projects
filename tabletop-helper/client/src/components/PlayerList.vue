<template>
    <div class="player-list">
        <PlayerCard v-for="player in players" :key="player.playerId" :player="player" />
    </div>
</template>

<script>
import PlayerCard from './PlayerCard.vue'

export default {
    components: {
        PlayerCard,
    },
    props: {
        players: {
            type: Array,
            required: true
        }
    }
}

</script>

<style>

</style>