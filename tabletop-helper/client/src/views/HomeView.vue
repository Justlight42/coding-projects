<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        <!-- <LoadingSpinner v-if="isLoading" :spin="isLoading"/> -->
        <CounterDownTimer />
      </h1>
    </div>

  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import CounterDownTimer from "../components/CountdownTimer.vue"

export default {
  components: {
    // LoadingSpinner,
    CounterDownTimer,
  },
  data() {
    return {
      isLoading: true,
    };
  },
 
};
</script>

<style scoped>



</style>