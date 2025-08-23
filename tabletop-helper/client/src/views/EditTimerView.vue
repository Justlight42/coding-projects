<template>
  <div class="edit-timer">
    <h3>Set the Timer</h3>
    <div class="timer-selector">
        <select :value="$store.state.timer.hours" @input="setHours($event.target.value)">
            <option v-for="hour in 24" :key="hour" :value="hour - 1">{{ hour - 1 }} {{ hour - 1 === 1 ? 'hr' : 'hrs' }}</option>
        </select>
                <select :value="$store.state.timer.minutes" @input="setMinutes($event.target.value)">
            <option v-for="minute in 60" :key="minute" :value="minute - 1">{{ minute - 1 }} {{ minute - 1 === 1 ? 'min' : 'mins' }}</option>
        </select>
                <select :value="$store.state.timer.seconds" @input="setSeconds($event.target.value)">
            <option v-for="second in 60" :key="second" :value="second - 1">{{ second - 1 }} {{ second - 1 === 1 ? 'sec' : 'secs' }}</option>
        </select>
    </div>
    <button @click="$router.back()">Set</button>
  </div>
</template>

<script>

export default {
    methods: {
        setHours(input) {
            this.$store.commit('SET_HOURS', Number(input))
        },
        setMinutes(input) {
            this.$store.commit('SET_MINUTES', Number(input))
        },
        setSeconds(input) {
            this.$store.commit('SET_SECONDS', Number(input))
        },
    }
}
</script>

<style>

</style>