<template>
  <div class="action-grid">
    <ActionCard v-for="action in actions" :key="action.actionId" :action="action" />
  </div>
</template>

<script>
import ActionCard from './ActionCard.vue';

export default {
  components: {
    ActionCard,
  },
  props: {
    actions: {
      type: Object,
      required: true
    }
  }
}
</script>

<style>

</style>