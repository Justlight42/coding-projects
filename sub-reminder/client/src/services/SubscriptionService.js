import axios from 'axios';

const BASE_SUB_PATH = 'api/sub'

export default {

  getSubById(subId) {
    return axios.get(`${BASE_SUB_PATH}/${subId}`);
  },

  getAllSubsByUserId(userId) {
    return axios.get(`${BASE_SUB_PATH}/user/${userId}`);
  },

  createSub(sub) {
    return axios.post(`${BASE_SUB_PATH}`, sub);
  },

  updateSub(sub, subId) {
    return axios.put(`${BASE_SUB_PATH}/${subId}`, sub);
  },

  deleteSub(subId) {
    return axios.delete(`${BASE_SUB_PATH}/${subId}`);
  }

}