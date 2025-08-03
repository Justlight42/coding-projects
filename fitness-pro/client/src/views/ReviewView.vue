<template>
  <div class="filterArea">
    <div class="filter-row">
      <label for="filterSelect">Filter: </label>
      <select id="filter-select" v-model="filter">
        <option value="username">Search by username</option>
        <option value="rating">Search by rating</option>
      </select>
    </div>
    <input id="filter-input" v-model="search" placeholder="Enter in the criteria" />
    <div class="filter-confirms">
      <button class="filter-button" v-on:click.prevent="applyFilter">Apply</button>
      <button class="filter-button" v-on:click.prevent="resetFilter">Reset</button>
    </div>
  </div>
  <div class="reviews-grid">
    <LoadingSpinner v-if="isLoading" :spin="isLoading" />
    <ReviewItem v-else v-for="review in $store.state.reviews" :key="review.reviewId" :review="review" :showWorkoutName="showWorkoutName" />
  </div>
</template>

<script>
import LoadingSpinner from "../components/LoadingSpinner.vue";
import ReviewItem from "../components/ReviewItem.vue";
import ReviewService from "../services/ReviewService";

export default {
  components: {
    ReviewItem,
    LoadingSpinner
  },
  data() {
    return {
      reviews: [],
      filter: null,
      search: '',
      isLoading: true,
      showWorkoutName: true
    };
  },
  methods: {
      getAllReviews() {
          ReviewService.getAllReviews().then((response) => {
              this.reviews = response.data;
              this.$store.commit("SET_REVIEWS", this.reviews)
              this.isLoading = false;
          });
      },
    applyFilter() {
        if (this.filter === 'username') {
        ReviewService.getReviewsByUsername(this.search).then((response) => {
          this.$store.commit("SET_REVIEWS", response.data);
        })
        .catch((error) => {
        console.log('Error occurred when filtering reviews by username:', error)
        })
      } else if (this.filter === 'rating') {
        ReviewService.getReviewsByRating(this.search).then((response) => {
          this.$store.commit("SET_REVIEWS", response.data);
        })
        .catch((error) => {
        console.log('Error occurred when filtering reviews by rating:', error)
        })
      }
    },
    resetFilter() {
        this.filter = '',
        this.search = '',
        this.getAllReviews();
    }
  },
  created() {
    this.getAllReviews();
  },
};
</script>

<style scoped>

.reviews-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

@media only screen and (max-width: 425px) {
  .reviews-grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }
}

</style>