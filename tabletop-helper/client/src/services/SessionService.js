import axios from 'axios';

const BASE_SESSION_PATH = 'api/session'

export default {
    
    getSessionById(sessionId) {
        return axios.get(`${BASE_SESSION_PATH}/${sessionId}`)
    },
    getSessionByUserId(userId) {
        return axios.get(`${BASE_SESSION_PATH}/${userId}`)
    },
    createSession(session) {
        return axios.post(`${BASE_SESSION_PATH}`, session)
    },
    endSession(sessionId) {
        return axios.put(`${BASE_SESSION_PATH}/${sessionId}`)
    },
    deleteSession(sessionId) {
        return axios.delete(`${BASE_SESSION_PATH}/${sessionId}`)
    },
    getGameModes() {
        return axios.get(`${BASE_SESSION_PATH}/game-modes`)
    },

}