<template>
  <div>
    <form v-on:submit.prevent="createTeam">
      <h1>Create A Team</h1>
      <div id="fields">
        <label for="team">Create A Team Name</label>
        <input
          type="text"
          id="team"
          placeholder="Team Name"
          v-model="team.teamName"
          required
          autofocus
        />
      </div>
      <div class="submit-button">
        <button type="submit">Create Team</button>  
        <router-link class="fake-button" :to="{name: 'AddPlayerView', query: {group: selectedGroup, gameMode: gameMode}}">Next</router-link>
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
            team: {
                teamName: '',
                sessionId: this.$route.params.sessionId
            },
            selectedGroup: this.$route.query.group,
            gameMode: this.$route.query.gameMode,
        }
    },
    methods: {
        createTeam() {
            TeamService.createTeam(this.team).then(() => {
                this.team.teamName = '';
                alert('Team has been created');
            })
            .catch((error) => {
                console.log('Error creating team', error)
                alert('Please create a unique name')
            })
        }
    }
}
</script>

<style>


</style>