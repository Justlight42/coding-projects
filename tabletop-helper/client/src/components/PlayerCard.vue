<template>
  <div class="player-card">
    <h2>{{ displayPlayer.name }}</h2>
    <p v-if="displayPlayer.teamName != null">Team Name: {{ displayPlayer.teamName }}</p>
    <p v-if="displayPlayer.health != null">Health: {{ displayPlayer.health }}</p>
    <p v-if="displayPlayer.score != null">Score: {{ displayPlayer.score }}</p>
    
    <div class="playerAction-controls">
      <button @click="switchSigns">
        {{ sign === '+' ? 'Add' : 'Subtract' }}
      </button>
      <input type="number" v-model.number="amount" placeholder="Enter Amount">
      <button @click="changeValue">Apply</button>
    </div>
  </div>
</template>

<script>
import PlayerActionService from '../services/PlayerActionService'
import PlayerService from '../services/PlayerService'

export default {
    props: {
        playerId: {
            type: Number,
            required: true
        }
    },
    data() {
      return {
        sign: '+',
        amount: ''
      }
    },
    computed: {
      displayPlayer() {
        return this.$store.state.players.find(p => p.playerId === this.playerId);
      }
    },
    methods: {
      switchSigns() {
        this.sign = this.sign === '+' ? '-' : '+';
      },
      changeValue() {
        const gameMode = this.$route.query.gameMode;
        const action = {
          sessionId: this.$route.params.sessionId,
          playerId: this.playerId,
          actionType: gameMode === '0' || gameMode === 0 ? 'score' : 'health',
          amount: this.sign === '+' ? this.amount : -this.amount,
          actionTime: '',
          actionReverted: '',
        }
        PlayerActionService.createAction(action).then(() => {
          PlayerService.getPlayerById(this.playerId)
            .then((response) => {
              const updatedPlayer = response.data;
              this.amount = ''
              this.$store.commit('UPDATE_PLAYER_STATE', updatedPlayer);
            })
        })
          .catch((error) => {
            console.log('Error occurred when creating a player action', error)
          })
      },
    },
}
</script>

<style>

</style>