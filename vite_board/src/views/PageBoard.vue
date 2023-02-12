<template>
  <div>
    <el-row>
      <el-col :span="8" />
      <el-col :span="8"
        ><div class="grid-content ep-bg-purple">
          <el-input v-model="searchQuery" placeholder="Search" class="input-with-select">
            <template #prepend>
              <el-select v-model="options.label" placeholder="Select" style="width: 115px">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </template>
          </el-input></div
      ></el-col>
      <el-col :span="8" />
    </el-row>
    <div class="grid-content ep-bg-purple"></div>
    <el-row>
      <el-col :span="2"> </el-col>
      <el-col :span="20">
        <el-table :data="resultQuery" style="width: 100%" @row-click="rowClicked">
          <el-table-column prop="boardid" label="boardid" width="80"> </el-table-column>
          <el-table-column prop="boardno" label="boardno" width="120"> </el-table-column>
          <el-table-column prop="title" label="title"> </el-table-column>
          <el-table-column prop="content" label="content"> </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-button type="success">
      <a href="/board/write" class="write_btn"><span>작성</span></a>
    </el-button>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, reactive } from 'vue';
import { useRouter } from 'vue-router';
import apiBoard from '@/api/board';

// const selected1 = ref();
const options = reactive([
  {
    value: 'boardid',
    label: 'boardid',
  },
  {
    value: 'boardno',
    label: 'boardno',
  },
  {
    value: 'title',
    label: 'title',
  },
  {
    value: 'content',
    label: 'content',
  },
]);

const router = useRouter();
const articles = ref(null);

onMounted(() => {
  apiBoard
    .getArticles(0)
    .then((response) => {
      articles.value = response.data;
      // console.log('1111 : ', response.data);
      // console.log('2222 : ', articles.value);
    })
    .catch((e) => {
      console.log(e);
    });
});

const searchQuery = ref();
const resultQuery = computed(() => {
  if (searchQuery.value) {
    if (options.label === 'title') {
      return articles.value.filter((item) => {
        return searchQuery.value
          .toLowerCase()
          .split(' ')
          .every((v) => item.title.toLowerCase().includes(v));
      });
    }
    if (options.label === 'id') {
      return articles.value.filter((item) => {
        return Number(searchQuery.value) === item.id;
      });
    }
    if (options.label === 'userId') {
      return articles.value.filter((item) => {
        return Number(searchQuery.value) === item.userId;
      });
    }
  }
  return articles.value;
});

function rowClicked(row) {
  // console.log('row: ', row.boardid);
  router.push({
    path: `/board/detail/${row.boardid}`,
  });
}
</script>
