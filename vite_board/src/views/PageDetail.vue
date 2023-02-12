<template>
  <div>
    <el-row>
      <el-col :span="2"></el-col>
      <el-col :span="20">
        <el-container>
          <el-header>제목 : {{ articles.title }}</el-header>
          <el-main v-html="articles.content"></el-main>
          <el-main v-for="(item, index) in fileList" :key="index">
            {{ item.fileName }}
          </el-main>
        </el-container>
      </el-col>
      <el-col :span="2"></el-col>
    </el-row>

    <br />
    <el-button type="success" @click="goBack">Back</el-button>
    <el-button type="danger" @click="editArticle">수정</el-button>
    <!-- <el-button type="info" @click="del">삭제</el-button> -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessageBox } from 'element-plus';
import apiBoard from '@/api/board';

const articles = ref('');
const router = useRouter();
const route = useRoute();
const fileList = ref();
// let articleList = [{title: 'title', content:'content', filename:'filename'}];

onMounted(() => {
  // console.log('Detail id: ', route.params.id);
  apiBoard
    .getArticle(route.params.id)
    .then((response) => {
      // console.log('detail 1: ', response.data);
      // console.log('detail 2: ', response.data.fileList);
      articles.value = response.data;
      articles.value = response.data;
      fileList.value = response.data.fileList;
    })
    .catch((e) => {
      console.log(e);
    });
});

const goBack = () => {
  router.push({ path: '/board' });
};
function editArticle() {
  router.push({ path: `/board/write/${route.params.id}` });
}
function del() {
  ElMessageBox.confirm('삭제하시겠습니까?', '삭제', {
    confirmButtonText: '확인',
    cancelButtonText: '취소',
    type: 'warning',
  }).then(() => {
    apiBoard
      .delete(route.params.id)
      .then((response) => {
        articles.value = response.data;
        // console.log('deleted success : ', response);
      })
      .catch((e) => {
        console.log(e);
      });
  });
}
</script>

<style>
.el-header {
  line-height: 60px;
  color: #333;
  text-align: left;
  background-color: b3c0d1;
}

.el-main {
  color: #333;
  text-align: left;
  background-color: #e9eef3;
}
</style>
