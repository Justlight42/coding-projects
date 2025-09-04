<template>
  <div class="action-card">
    <p>{{ player.name }}</p>
    <p>{{ action.actionType }}</p>
    <p>{{ action.amount }}</p>
    <p>{{ action.actionTime }}</p>
    <p>{{ action.actionReverted }}</p>
  </div>
</template>

<script>
import PlayerService from '../services/PlayerService';

export default {
  props: {
    action: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      player: {
        name: ''
      }
    }
  },
  methods: {
    getPlayerById() {
      PlayerService.getPlayerById(this.action.playerId).then((response) => {
        this.player = response.data;
      })
      .catch((error) => {
        console.log('Error occurred when get player by ID for action', error)
      })
    }
  },
  created() {
    this.getPlayerById();
  }
}
</script>

<style>

</style>