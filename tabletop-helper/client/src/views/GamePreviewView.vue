<template>
  <div>
    <form v-on:submit.prevent="createSession">
      <h1>Create A Game Session</h1>
      <div id="fields">
        <label for="gameMode">Select A Game Mode</label>
        <select v-model="session.gameMode" name="gameMode" id="gameMode">
            <option v-for="gameMode in gameModes" :key="gameMode.id" :value="gameMode.id">
                {{ gameMode.name }}
            </option>
        </select>
        <label for="groups">Select Either Team or Individuals</label>
        <select v-model="selectedGroup" name="groups" id="groups">
            <option v-for="group in groups" :key="group" :value="group">
                {{ group }}
            </option>
        </select>
        <label for="user">Session Owner</label>
        <input
          type="text"
          id="user"
          placeholder="User"
          :value="$store.state.user.username"
          readonly
        />
      </div>
      <div class="submit-button">
        <button type="submit">Submit</button>  <!-- Needs to submit and createSession will route to player--> <!-- Needs to also route if player or team is selected-->
      </div>
      <hr/>
    </form>
  </div>
</template>

<script>
import SessionService from '../services/SessionService';

export default {
    data() {
        return {
            session: {
                gameMode: '',
                createdById: this.$store.state.user.id
            },
            gameModes: [],
            groups: ['Individuals', 'Team'],
            selectedGroup: 'Individuals',
        };
    },
    methods: {
        getGameModes() {
            SessionService.getGameModes().then((response) => {
                this.gameModes = response.data;
            })
        },
        createSession() {
            SessionService.createSession(this.session).then((response) => {
                if (this.selectedGroup === 'Team') {
                    this.$router.push({ name: "AddTeamView", params: {sessionId: response.data.sessionId}, query: {group: this.selectedGroup, gameMode: this.session.gameMode}});
                } else {
                    this.$router.push({ name: "AddPlayerView", params: {sessionId: response.data.sessionId}, query: {group: this.selectedGroup, gameMode: this.session.gameMode}}) 
                }
            })
            .catch((error) => {
                console.log('Error occurred when creating a session.')
            })
        }
    },
    created() {
        this.getGameModes()
    }

}
</script>

<style>

</style>