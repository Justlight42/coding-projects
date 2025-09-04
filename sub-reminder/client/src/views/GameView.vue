<template>
  <div class="game-view">
    <div class="game-timers">
      <CountdownTimer />
      <SessionTimer v-if="session && session.startTime" :session="session" />
    </div>
    <PlayerList />
  </div>
</template>

<script>
import PlayerList from '../components/PlayerList.vue';
import PlayerService from '../services/PlayerService';
import CountdownTimer from '../components/CountdownTimer.vue';
import SessionTimer from '../components/SessionTimer.vue';
import SessionService from '../services/SessionService';

export default {
  components: {
    PlayerList,
    CountdownTimer,
    SessionTimer
  },
  data() {
    return {
      session: {}
    };
  },
  methods: {
    getPlayerInSession() {
      PlayerService.getPlayerInSession(this.$route.params.sessionId).then((response) => {
        this.$store.commit('SET_PLAYERS', response.data);
      })
      .catch((error) => {
        console.log('Error occurred when getting all the players in the session', error);
      })
    },
    getSessionById() {
      SessionService.getSessionById(this.$route.params.sessionId).then((response) => {
        this.session = response.data;
      })
      .catch((error) => {
        console.log('Error occurred when getting the session', error);
      })
    }
  },
  created() {
    this.getPlayerInSession();
    this.getSessionById();
  }
}
</script>

<style scoped>

.game-timers {
  display: flex;
}

/* Comes from CountdownTimer.vue */
::v-deep .countdown-timer {
  display: flex;
  flex-direction: row;
}

::v-deep .countdown-timer h2 {
  font-size: 20px;
}

</style>