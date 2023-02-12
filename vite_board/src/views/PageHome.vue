<!-- PageHome.vue -->
<template>
  <div class="home">
    <!-- <img alt="Vue logo" src="../assets/logo.png"> -->
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>

<script>
// Home page
import axios from 'axios';
import HelloWorld from '@/components/HelloWorld.vue';

export default {
  name: 'PageHome',
  components: {
    HelloWorld,
  },
  setup() {
    axios.get('/api/items').then((res) => {
      console.log(res);
    });
  },
};
</script>
