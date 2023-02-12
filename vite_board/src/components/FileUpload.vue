<template>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card>
        <el-upload
          :value="props.fileList"
          class="upload-demo"
          :http-request="upload"
          multiple
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :limit="3"
          :on-exceed="handleExceed"
          :before-upload="handleUploadbefore"
        >
          <el-button type="primary">추가</el-button>
          <template #tip>
            <div class="el-upload__tip">jpg/png files with a size less than 500KB.</div>
          </template>
        </el-upload>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
</template>
<script setup>
import { ref, defineProps, defineEmits, reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import axios from 'axios';
import apiBoard from '@/api/board';

const typeFomat = ref();
const uploadPath = ref();
const fileName = ref();
const uuid = ref();
const uploadFolder = ref();
const uploadPathFolder = ref();
const fileList = reactive([]);
const route = useRoute();

const props = defineProps({
  fileList: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(['update:fileList']);

// 보이기
onMounted(() => {
  if (route.params.id) {
    apiBoard
      .getArticle(route.params.id)
      .then((response) => {
        fileName.value = response.data.filename;
      })
      .catch((e) => {
        console.log(e);
      });
  }
});
// 첨부파일 보이기
function upload(param) {
  const formData = new FormData();
  formData.append('file', param.file);
  const url = 'http://localhost:8080/api/board/uploadByAction';
  axios
    .post(url, formData)
    .then((res) => {
      console.log('fu: ', res.data);
      typeFomat.value = res.data.type;
      uploadPath.value = res.data.datePath.replace(/\\/g, '/');
      fileName.value = res.data.name;
      uuid.value = res.data.uuid;
      uploadFolder.value = res.data.uploadFolder.replace(/\\/g, '/');
      uploadPathFolder.value = `${uploadFolder.value}/${uploadPath.value}`;
      fileList.push({
        uploadPath: uploadPathFolder.value,
        fileName: fileName.value,
        uuid: uuid.value,
        type: typeFomat.value,
      });
      emit('update:fileList', fileList);
    })
    .catch((err) => {
      console.error('Error:', err);
      ElMessage.error('첨부 파일이 아닙니다.');
    });
}

const handleRemove = (file, uploadFiles) => {
  console.log('handleRemove: ', file, uploadFiles);
};

const handleUploadbefore = (file) => {
  console.log('handleUploadBefore', file);
};

const handlePreview = (uploadFile) => {
  console.log('handlePreview: ', uploadFile);
};

const handleExceed = (files, uploadFiles) => {
  ElMessage.warning(
    `The limit is 3, you selected ${files.length} files this time, add up to ${
      files.length + uploadFiles.length
    } totally`,
  );
};

const beforeRemove = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(`Cancel the transfert of ${uploadFile.name},${uploadFiles} ?`).then(
    () => true,
    () => false,
  );
};
</script>
