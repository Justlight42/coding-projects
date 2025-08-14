<template>
  <div id="tabletop-app">
    <header>
      <div class="header-left">
        <router-link class="router-style" v-bind:to="{ name: 'home' }">
          Tabletop Helper
        </router-link>
      </div>
      <nav>
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token">
          Logout
        </router-link>
        <router-link v-bind:to="{ name: 'login' }" v-else>
          Login
        </router-link>
      </nav>
    </header>

    <main>
      <router-view />
    </main>
    <footer>&copy; Tabletop Helper. All Rights Reserved</footer>
  </div>
</template>

<style>

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#tabletop-app {
  box-sizing: border-box;
  height: 98vh;
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
  justify-content: space-evenly;
}

header div {
  display: flex;
  flex-grow: 1;
  align-items: center;
}

header img {
  height: 100px;
  width: auto;
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
  background-color: mintcream;
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
  background-color: rgb(230, 216, 181);
  grid-area: main;
  overflow: auto;
}

footer {
  background-color: mintcream;
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



</style>