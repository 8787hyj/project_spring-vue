<template>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card>
        <div>
          <router-link :to="{ path: '/board' }">
            <el-button type="success">Back</el-button>
          </router-link>
          <span>{{ getTitle }}</span>
          <el-button type="success" @click="writeArticle">저장</el-button>
        </div>
        <br /><br />
        <div>
          <el-form label-width="100px">
            <el-input v-model="title" type="text" placeholder="제목을 입력하세요"></el-input>
            <QuillEditor
              ref="myEditor"
              v-model:content="modelValue"
              theme="snow"
              :modules="modules"
              :toolbar="editorOption"
              content-type="html"
              @change="onEditorChange"
            />
          </el-form>
        </div>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
  <FileUpload v-model:file-list="fileList"></FileUpload>
  <span v-for="(item, index) in fileLists" :key="index">
    {{ item.fileName }}
    <el-button v-show="item.fileName" type="primary" @click="del">삭제</el-button>
  </span>
  <hr />
  <input type="button" value="데이터확인용" @click="save()" />
</template>

<script setup>
import { ref, computed, onMounted, reactive } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import { QuillEditor } from '@vueup/vue-quill';
import BlotFormatter from 'quill-blot-formatter';
import ImageUploader from 'quill-image-uploader';
import axios from 'axios';
import apiBoard from '@/api/board';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
import FileUpload from '@/components/FileUpload.vue';

const router = useRouter();
const route = useRoute();

// 게시판
const title = ref('');
const writer = ref();
const boardno = ref();
const myEditor = ref('');
const modelValue = ref(''); // content

// 이미지 파일
const imageLists = ref([]);

// 첨부 파일
const fileLists = ref();
const fileList = ref([]);
const fileName = ref();

// 백단으로 넘길 폼데이터
const formData = new FormData();

const editorOption = reactive([
  ['bold', 'italic', 'underline', 'strike'], // <strong>, <em>, <u>, <s>
  ['blockquote', 'code-block'], // <blockquote>, <pre class="ql-syntax" spellcheck="false">
  [{ header: 1 }, { header: 2 }], // <h1>, <h2>
  [{ list: 'ordered' }, { list: 'bullet' }],
  [{ script: 'sub' }, { script: 'super' }], // <sub>, <sup>
  [{ indent: '-1' }, { indent: '+1' }], // class제어
  [{ direction: 'rtl' }],
  [{ size: ['small', false, 'large', 'huge'] }],
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // <h1>, <h2>, <h3>, <h4>, <h5>, <h6>, normal
  [{ font: [] }], // 글꼴 class로 제어
  [{ color: [] }, { background: [] }],
  [{ align: [] }], // class
  ['clean'],
  ['link', 'image', 'video'],
]);

const modules = [
  {
    name: 'imageUploader',
    module: ImageUploader,
    options: {
      upload: (file) => {
        return new Promise((resolve, reject) => {
          formData.append('image', file);

          axios
            .post('http://localhost:8080/api/board/upload-image', formData)
            .then((res) => {
              const imageView = `${res.data.url}${res.data.datePath.replace(/\\/g, '/')}/s_${res.data.uuid}_${
                res.data.name
              }`;
              resolve(imageView);
              imageLists.value = res.data.imageList;
            })
            .catch((err) => {
              reject(err);
              console.error('Error:', err);
              ElMessage.error('이미지 파일이 아닙니다.');
            });
        });
      },
    },
  },
  {
    name: 'blotFormatter',
    module: BlotFormatter,
    options: {},
  },
];

const getTitle = computed(() => {
  if (route.params.id) return '게시물 수정하기';
  return '게시물 작성하기';
});

const save = () => {
  console.log('DB fileLists.value: ', fileLists.value);
  console.log('view fileList.value: ', fileList.value);
};

// 이미지 보이기
onMounted(() => {
  if (route.params.id) {
    apiBoard
      .getArticle(route.params.id)
      .then((response) => {
        title.value = response.data.title;
        modelValue.value = response.data.content;
        fileName.value = response.data.filename;
        fileLists.value = response.data.fileList;
        imageLists.value = response.data.imageList;
        console.log('pw: ', response.data.fileList);
      })
      .catch((e) => {
        console.log(e);
      });
  }
});

function writeArticle() {
  // 저장
  if (route.params.id !== '') {
    axios
      .patch(`http://localhost:8080/api/board/post/update/${route.params.id}`, {
        content: myEditor.value.getHTML(),
        title: title.value,
        writer: writer.value,
        boardno: boardno.value,
        imageList: imageLists.value,
        fileList: fileList.value,
      })
      .then((response) => {
        console.log('patchArticle: ', response.data);
        router.push({ path: `/board/write/${route.params.id}` });
        // router.push({ path: `/board/detail/${route.params.id}` });
      })
      .catch((e) => {
        console.log(e);
        ElMessage.error('게시물 수정 중 에러가 발생하였습니다.');
      });
  } else {
    axios
      .post('http://localhost:8080/api/board/goodsEnroll', {
        content: myEditor.value.getHTML(),
        title: title.value,
        writer: writer.value,
        boardno: boardno.value,
        imageList: imageLists.value,
        fileList: fileList.value,
      })
      .then((res) => {
        console.log(res);
        // router.push({ path: '/board/write' });
        router.push({ path: '/board' });
      })
      .catch((err) => {
        console.error('Error:', err);
        ElMessage.error('게시물 작성 중 에러가 발생하였습니다.');
      });
  }
}

function onEditorChange({ quill, html, text }) {
  modelValue.value = html;
  console.log('editor change!', quill, html, text);
}
</script>

<style scoped>
.left-button {
  float: left;
  padding: 3px 0;
}

.right-button {
  float: right;
  padding: 3px 0;
}
</style>
