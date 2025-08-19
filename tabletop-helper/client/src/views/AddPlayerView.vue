<template>
  <div>
    <form v-on:submit.prevent="createPlayer">
      <h1>Create A Player</h1>
      <div id="fields">
        <span v-if="selectedGroup === 'Team'">
        <label for="group">Select A Team</label>
        <select v-model="player.teamId" name="teamId" id="teamId">
            <option v-for="team in teams" :key="team.teamId" :value="team.teamId">
                {{ team.teamName }}
            </option>
        </select>
        </span>
        <label for="name">Create A Player Name</label>
        <input
          type="text"
          id="name"
          placeholder="Player Name"
          v-model="player.name"
          required
          autofocus
        />
        <span v-if="gameMode === '0'">
        <label for="score">Starting Score</label>
        <input
          type="number"
          id="score"
          placeholder="Starting Score"
          v-model="player.score"
          required
        />
        </span>
        <span v-if="gameMode === '1'">
        <label for="score">Starting Health</label>
        <input
          type="number"
          id="health"
          placeholder="Starting Health"
          v-model="player.health"
          required
        />
        </span>
      </div>
      <div class="submit-button">
        <button type="submit">Submit</button>  <!-- Needs to submit and createSession will route to player-->
      </div>
      <hr/>
    </form>
  </div>
</template>

<script>
import TeamService from '../services/TeamService';

export default {
    data() {
        return {
            player: {
                teamId: '',
                userId: this.$store.state.user.id,
                name: '',
                health: '',
                score: ''
            },
            teams: [],
            gameMode: this.$route.query.gameMode
        };
    },
    methods: {
        getTeamsBySessionId() {
            TeamService.getTeamsBySessionId(this.$route.params.sessionId).then((response) => {
                this.teams = response.data;
            })
            .catch((error) => {
                console.log('Error has occurred when getting teams by session ID ', error)
            })
        }
    },
    computed: {
        selectedGroup() {
            return this.$route.query.group
        }
    },
    created() {
        if (this.selectedGroup === 'Team') {
            this.getTeamsBySessionId();
        }
    }
}
</script>

<style>

</style>