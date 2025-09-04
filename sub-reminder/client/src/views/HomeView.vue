<template>
  <div class="home">
      <CounterDownTimer />
  </div>
</template>

<script>
import CounterDownTimer from "../components/CountdownTimer.vue"

export default {
  components: {
    CounterDownTimer,
  },
 
};
</script>

<style scoped>

.home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  flex: 1;
  height: 100%
}



</style>