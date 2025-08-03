import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'
import ExerciseView from '../views/ExerciseView.vue'
import AddExerciseView from '../views/AddExerciseView.vue'
import UpdateExerciseView from '../views/UpdateExerciseView.vue'
import ReviewView from '../views/ReviewView.vue'
import AddReviewView from '../views/AddReviewView.vue'
import UserExerciseListView from '../views/UserExerciseListView.vue'
import UpdateExerciseListView from '../views/UpdateExerciseListView.vue'
import AddListView from '../views/AddListView.vue'
import AddExerciseToListView from '../views/AddExerciseToListView.vue'
import DeleteExerciseFromListView from '../views/DeleteExerciseFromListView.vue'

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
    {
    path: '/userExiList',
    name: 'UserExerciseListView',
    component: UserExerciseListView,
    meta: {
      requiresAuth: true
    }
  },
      {
    path: '/userExiList/list',
    name: 'AddListView',
    component: AddListView,
    meta: {
      requiresAuth: true
    }
  },
   {
    path: '/userExiList/deleteList/:listId',
    name: 'DeleteExerciseFromListView',
    component: DeleteExerciseFromListView,
    meta: {
      requiresAuth: true
    }
  },
    {
    path: '/userExiList/list/:exerciseId',
    name: 'AddExerciseToListView',
    component: AddExerciseToListView,
    meta: {
      requiresAuth: true
    }
  },
      {
    path: '/userExiList/list/:listId',
    name: 'UpdateExerciseListView',
    component: UpdateExerciseListView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/exercise/:workoutName/details/:exerciseId',
    name: 'ExerciseView',
    component: ExerciseView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/exercise',
    name: 'addExercise',
    component: AddExerciseView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/exercise/:exerciseId',
    name: 'updateExercise',
    component: UpdateExerciseView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/reviews',
    name: 'ReviewView',
    component: ReviewView,
    meta: {
      requiresAuth: false
    }
  },
    {
    path: '/reviews/:exerciseId',
    name: 'AddReviewView',
    component: AddReviewView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return { name: "login" };
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
