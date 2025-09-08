<template>
  <div class="reminder-card">
    <div class="reminder-topCard">
      <h2>{{ reminder.subName }}</h2>
      <p>{{ reminder.reminderDate }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    reminder: Object
  },

}
</script>

<style>

</style>