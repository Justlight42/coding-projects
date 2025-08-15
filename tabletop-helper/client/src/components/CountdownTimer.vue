<template>
  <div class="countdown-timer">
    <h2>{{ displayTimer }}</h2>
    <div class="timer-buttons">
      <router-link class="fake-button" :to="{name: 'EditTimerView'}">Edit Timer</router-link>
      <button @click="toggleTimer">{{ timerIsOn ? 'Stop' : 'Start' }}</button>
      <button v-on:click="resetTimer">
        Reset
      </button>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      timerInterval: null,
    };
  },
  computed: {
    displayTimer() {
      const { hours, minutes, seconds} = this.$store.state.timer;
      return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`
    },
    timerIsOn() {
      return this.$store.state.timerIsOn;
    }
  },
  methods: {
    toggleTimer() {
      if (this.$store.state.timerIsOn) {
        clearInterval(this.timerInterval);
        this.$store.commit('SET_TIMER_IS_ON', false);
      } else {
        this.$store.commit('SET_TIMER_IS_ON', true);
        this.timerInterval = setInterval(this.incrementTime, 1000)
      }
    },
    incrementTime() {
      const time = this.$store.state.timer;
      if (time.hours === 0 && time.minutes === 0 && time.seconds === 0) {
        clearInterval(this.timerInterval);
        this.$store.commit('SET_TIMER_IS_ON', false);
        return alert('Time is Up!');
      }
      if (time.seconds > 0) {
        time.seconds--;
      } else if (time.minutes) {
        time.minutes--;
        time.seconds = 59;
      } else if (time.hours) {
        time.hours--;
        time.minutes = 59;
        time.seconds = 59;
      }
    },
    resetTimer() {
      clearInterval(this.timerInterval);
      this.$store.commit('RESET_TIMER');
    }
  },
  beforeUnmount() {
    clearInterval(this.timerInterval);
  }

}
</script>

<style>

</style>