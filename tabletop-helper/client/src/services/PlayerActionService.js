import axios from 'axios';

const BASE_PLAYER_ACTION_PATH = 'api/playerAction'

export default {

    getActionById(actionId) {
        return axios.get(`${BASE_PLAYER_ACTION_PATH}/${actionId}`)
    },
    getActionByPlayerId(playerId) {
        return axios.get(`${BASE_PLAYER_ACTION_PATH}/actions/${playerId}`)
    },
    getAllActionsInSession(sessionId) {
        return axios.get(`${BASE_PLAYER_ACTION_PATH}/sessionActions/${sessionId}`)
    },
    createAction(playerAction) {
        return axios.post(`${BASE_PLAYER_ACTION_PATH}`, playerAction)
    },
    revertAction(actionId) {
        return axios.put(`${BASE_PLAYER_ACTION_PATH}/${actionId}/revert`)
    }

}