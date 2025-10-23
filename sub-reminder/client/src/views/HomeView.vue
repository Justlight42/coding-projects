<template>
  <div class="landing-page">
    <!-- Introduction Section -->
    <section class="introduction">
      <h1>Welcome to Sub Reminder</h1>
      <p>Manage your subscriptions efficiently and never miss a payment.</p>
    </section>

    <!-- Features Section -->
    <section class="features">
      <div class="feature-card" v-for="feature in features" :key="feature.title">
        <img :src="feature.icon" alt="icon" />
        <h3>{{ feature.title }}</h3>
        <p>{{ feature.description }}</p>
      </div>
    </section>

    <!-- How it Works Section -->
    <section class="steps">
      <h2>How it Works</h2>
      <ol>
        <li v-for="step in steps" :key="step.number">
          <strong>Step {{ step.number }}:</strong> {{ step.text }}
        </li>
      </ol>
      <button @click="goToSelectedPage">{{ Object.keys($store.state.user).length > 0 ? 'Go to Subscriptions' : 'Get Started →' }}</button>
    </section>
  </div>
</template>
    
<script>

export default {
  methods: {
    goToSelectedPage() {
      if (this.$store.state.user) {
        this.$router.push('/mySubs')
      } else {
        this.$router.push('/login')
      }
    }
  },
  data() {
    return {
      features: [
        { title: 'Track Payments', description: 'Keep track of all your subscriptions in one place.', icon: 'src/assets/payment_img.jpg' },
        { title: 'Reminders', description: 'Never miss a payment with automatic reminders.', icon: 'src/assets/reminder_img.png' },
      ],
      steps: [
        { number: 1, text: 'Sign up or log in to your account' },
        { number: 2, text: 'Connect your subscriptions' },
        { number: 3, text: 'Manage and track them easily' }
      ]
    }
  }
}
</script>

<style scoped>

.landing-page {
  font-family: sans-serif;
  text-align: center;
  padding: 3rem;
  line-height: 1.6; 
}

/* Introduction Section */
.introduction h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.introduction p {
  font-size: 1.3rem;
  max-width: 600px;
  margin: 0 auto 2rem auto;
}

/* Features Section */
.features {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-bottom: 2rem;
  flex-wrap: wrap;
}

.feature-card {
  width: 300px;
  padding: 2rem;
  border-radius: 16px;
  text-align: center;
  background-color: #0a8d50;

  box-shadow: 0 8px 20px rgba(0,0,0,0.15);
  transition: transform 0.3s, box-shadow 0.3s;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 25px rgba(0,0,0,0.25);
}

.feature-card img {
  width: 100px;
  height: 100px;
  object-fit: contain;
  margin-bottom: 1rem;
}

.feature-card h3 {
  font-size: 1.6rem;
  margin: 1rem 0 0.5rem;
}

.feature-card p {
  font-size: 1.3rem;
  color: white;
}

/* Steps Section */
.steps h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.steps ol {
  text-align: left;
  max-width: 400px;
  margin: 0 auto 3rem auto;
  font-size: 1.3rem;
}

.steps li {
  font-size: 1.3rem; 
  line-height: 1.8; 
  margin-bottom: 1rem;   
}

.steps li strong {
  font-size: 1.4rem;
  color: #000000;
}


/* Button */
.steps button {
  font-size: 1.5rem;
  padding: 1rem 2.5rem;
  border-radius: 8px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  transition: background-color 0.3s ease;
}

.steps button:hover {
  background-color: #0056b3;
}

</style>
