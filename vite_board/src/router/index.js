import { createRouter, createWebHistory } from 'vue-router';

// import demoRouters from './modules/demo';

const routes = [
  {
    path: '/',
    name: 'PageHome',
    component: () => import(/* webpackChunkName: "about" */ '@/views/PageHome.vue'),
  },
  {
    path: '/about',
    name: 'PageAbout',
    component: () => import(/* webpackChunkName: "about" */ '@/views/PageAbout.vue'),
  },
  {
    path: '/board',
    name: 'PageBoard',
    component: () => import(/* webpackChunkName: "about" */ '@/views/PageBoard.vue'),
  },
  {
    path: '/board/detail/:id',
    name: 'Detail',
    component: () => import(/* webpackChunkName: "about" */ '@/views/PageDetail.vue'),
  },
  {
    path: '/board/write/:id?',
    name: 'Write',
    component: () => import(/* webpackChunkName: "about" */ '@/views/PageWrite.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior() {
    return {
      el: '#app',
      top: 0,
      behavior: 'smooth',
    };
  },
});

export default router;
