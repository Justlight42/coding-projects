<template>
  <div id="subReminder-app">
    <header>
      <div class="header-left">
        <router-link class="router-style" v-bind:to="{ name: 'home' }">
          Sub Reminder
        </router-link>
      </div>
      <nav>
        <div class="profile-dropdown">
          <img src="/img/profile-placeholder.png" alt="Profile" @click="toggleDropdown" />
          <div v-if="showDropdown" class="dropdown-menu">
            <router-link :to="{ name: 'SubscriptionView' }" @click="showDropdown = false">
              My Subscriptions
            </router-link>
            <router-link :to="{ name: 'home' }" @click="showDropdown = false">
              Calender
            </router-link>
            <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token" @click="showDropdown = false">
              Logout
            </router-link>
            <router-link v-bind:to="{ name: 'login' }" @click="showDropdown = false" v-else >
              Login
            </router-link>
          </div>
        </div>
      </nav>
    </header>

    <main>
      <router-view />
    </main>
    <footer>&copy; Sub Helper. All Rights Reserved</footer>
  </div>
</template>

<script>

export default {
  data() {
    return {
      showDropdown: false
    }
  },
  methods: {
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    }
  }
}
</script>

<style>

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#subReminder-app {
  box-sizing: border-box;
  height: 100vh;
  font-family: Arial, Helvetica, sans-serif;
  background-color: lightsteelblue;
  display: grid;
  gap: 5px;
  padding: 5px;
  grid-template-columns: 1fr;
  grid-template-rows: auto 1fr auto;
  grid-template-areas:
    "header"
    "main"
    "footer";
}

header nav {
  display: flex;
  flex-grow: .25;
  align-items: center;
  justify-content: flex-end;
}

header div {
  display: flex;
  flex-grow: 1;
  align-items: center;
}

.profile-dropdown img {
  height: 40px;
  width: 40px;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
}

.profile-dropdown {
  display: flex;
  position: relative;
  align-items: center;
  justify-content: flex-end; 
  margin-left: auto;
  margin-right: 20px
}

.dropdown-menu {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: absolute;
  top: calc(100% + 5px); 
  min-width: 100px;
  background-color: lightgray;
  border: 1px solid #ccc;
  padding: 0.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  gap: 0.5rem;
}

header h1 {
  text-align: left;
  flex-grow: 1;
  color: navy;
  margin-left: 10px;
  font-size: larger;
}

header {
  grid-area: header;
  align-content: center;
  justify-content: center;
  display: flex;
  background-color: rgb(159, 222, 241);
}

header form {
  padding-top: 4px;
  padding-bottom: 4px;
  display: flex;
  align-content: center;
  justify-content: center;
}

header form input {
  height: 20px;
  font-size: larger;
  box-shadow: 2px 2px 2px #777;
}

nav {
  flex-grow: 1;
}

main,
header,
footer {
  border-radius: 5px;
  padding: 5px;
}

main {
  background-color: rgb(129, 128, 125);
  grid-area: main;
  overflow: auto;
  display: flex;
  height: 100%;
  flex-direction: column;
}

footer {
  background-color: rgb(159, 222, 241);
  grid-area: footer;
}

button {
  padding: 0.75rem 1.5rem;
  background-color: lightsteelblue;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

button:active {
  -webkit-box-shadow: inset 0px 0px 5px #c1c1c1;
  -moz-box-shadow: inset 0px 0px 5px #c1c1c1;
  box-shadow: inset 0px 0px 5px #c1c1c1;
  outline: none;
}

button:hover {
  background-color: steelblue;
}

form {
  max-width: 500px;
  margin: 2rem auto;
  padding: 2rem;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

form label {
  font-size: 1rem;
  color: #333;
  margin-bottom: 0.5rem;
  display: block;
}

form input[type="text"],
form input[type="password"],
form input[type="number"],
form select,
form textarea {
  width: 100%;
  padding: 0.75rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #fff;
  transition: border-color 0.3s ease;
  margin-bottom: 1rem;
}

form input[type="text"]:focus,
form input[type="password"]:focus,
form input[type="number"]:focus,
form select:focus,
form textarea:focus {
  border-color: lightsteelblue;
  outline: none;
}

form textarea {
  resize: vertical;
  height: 120px;
}

form div {
  margin-bottom: 1.5rem;
}

form h1 {
  display: flex;
  justify-content: center;
  font-size: 20px;
  margin-bottom: 8px;
}

form span {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-top: 5px;
}

.fake-button {
  padding: 0.75rem 1.5rem;
  background-color: lightsteelblue;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
  text-decoration: none;
}

.fake-button:hover {
  background-color: steelblue;
}

.fake-button:active {
  background-color: #004080;
}

.router-style {
  text-decoration: none;
}

.header-left {
  margin-left: 40px;
}

.submit-button {
  display: flex;
  justify-content: center;
  gap: 10px;
}

/* VueDatePicker internal layout overrides */
.dp__main {
  width: 100%;
  height: 15px;
  max-height: none;
  padding: 0;
}
.dp--header-wrap {
  margin-bottom: -20px;
}

.dp__month_year_row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.5rem;
  padding: 0.25rem 0.5rem;
}

.dp__calendar_header {
  margin-bottom: 0.25rem;
  padding: 0;
}

.dp__calendar {
  padding: 0;
  margin-bottom: 0.25rem;
  row-gap: 0.25rem;
}

.dp__menu_inner {
  margin-bottom: -40px;
}

.dp__month_year_wrap .dp__month_year_wrap {
  display: flex;
  align-items: center;
  padding: 0.25rem;
  gap: 0.5rem;
  margin-bottom: 0;
}

.dp__action_row {
  margin-bottom: 0;
}

/* Fullscreen overlay */
.popup-screen {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  overflow-y: auto;

}

/* Modal container */
.calender-popup {
  background-color: #fff;
  padding: 0.75rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  width: 340px;
  max-width: 90vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
}

/* Buttons */
.calender-popup .calender-cancel {
  margin: 0.25rem;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.calender-confirm {
  background-color: #3ea800;
}


</style>