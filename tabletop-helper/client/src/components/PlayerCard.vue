<template>
  <div class="player-card">
    <div class="player-info">
    <h2>{{ displayPlayer.name }}</h2>
    <h2 v-if="displayPlayer.teamName != null">Team: {{ displayPlayer.teamName }}</h2>
    </div>
    <div class="player-scoring">
    <p v-if="displayPlayer.health != null">Health: {{ displayPlayer.health }}</p>
    <p v-if="displayPlayer.score != null">Score: {{ displayPlayer.score }}</p>
    </div>
    
    <div class="playerAction-controls">
      <button @click="switchSigns">
        {{ sign === '+' ? '+' : '-' }}
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

<style scoped>

.player-card {
  display: grid;
  grid-template-rows: auto auto;
  background-color: gray;
  border: 1px solid;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.player-info {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.player-info h2 {
  font-size: 30px;
}

.player-scoring {
  display: flex;
  justify-content: center;
  padding: 30px;
}

.player-scoring p {
  font-size: 30px;
}

.playerAction-controls {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

</style>