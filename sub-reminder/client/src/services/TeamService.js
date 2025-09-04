import axios from 'axios';

const BASE_TEAM_PATH = 'api/team'

export default {
    
    getTeamId(teamId) {
        return axios.get(`${BASE_TEAM_PATH}/${teamId}`)
    },
    getTeamsBySessionId(sessionId) {
        return axios.get(`${BASE_TEAM_PATH}/session/${sessionId}`)
    },
    createTeam(team) {
        return axios.post(`${BASE_TEAM_PATH}`, team)
    },
    updateTeamName(team, teamId) {
        return axios.put(`${BASE_TEAM_PATH}/${teamId}`, team)
    },
    deleteTeam(teamId) {
        return axios.delete(`${BASE_TEAM_PATH}/${teamId}`)
    }

}