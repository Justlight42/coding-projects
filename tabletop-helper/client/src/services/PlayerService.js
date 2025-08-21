import axios from 'axios';

const BASE_PLAYER_PATH = 'api/player'

export default {
    
    getPlayerById(playerId) {
        return axios.get(`${BASE_PLAYER_PATH}/${playerId}`)
    },
    getPlayerInSession(sessionId) {
        return axios.get(`${BASE_PLAYER_PATH}/session/${sessionId}`)
    },
    createPlayer(player) {
        return axios.post(`${BASE_PLAYER_PATH}`, player)
    },
    updatePlayer(player, playerId) {
        return axios.put(`${BASE_PLAYER_PATH}/${playerId}`, player)
    },
    deletePlayer(playerId) {
        return axios.delete(`${BASE_PLAYER_PATH}/${playerId}`)
    }

}