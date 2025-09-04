<template>
  <div class="session-timer">
    <p>Session Time: </p>
    <p>{{ formattedTime }}</p>
  </div>
</template>

<script>

export default {
  props: {
    session: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      elapsed: 0,
      timer: null
    };
  },
  computed: {
    formattedTime() {
      const hours = String(Math.floor(this.elapsed / 3600)).padStart(2, '0');
      const minutes = String(Math.floor((this.elapsed % 3600) / 60)).padStart(2, '0');
      const seconds = String(this.elapsed % 60).padStart(2, '0');
      return `${hours}:${minutes}:${seconds}`;
    }
  },
 methods: {
  startTimer() {
    const start = new Date(this.session.startTime).getTime();
    const end = this.session.endTime ? new Date(this.session.endTime).getTime() : null;

    this.timer = setInterval(() => {
      const now = Date.now();

      if (end && now >= end) {
        this.elapsed = Math.floor((end - start) / 1000);
        clearInterval(this.timer); // stop ticking
      } else {
        this.elapsed = Math.floor((now - start) / 1000);
      }
    }, 1000);
  }
},
  created() {
    this.startTimer();
  }
};
</script>

<style scoped>

.session-timer {
  display: flex;
  align-items: center;
  margin-right: 10px;
  gap: 0.5rem;
  white-space: nowrap;
}

</style>