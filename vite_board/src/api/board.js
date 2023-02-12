import axios from 'axios';

const BASE_URL2 = 'http://localhost:8080/api/board';
// const PAGESIZE = 10;

export default {
  // 게시판 목록
  getArticles(page) {
    console.log('list : ', page);
    return axios.get(`${BASE_URL2}/post/list`);
    // return axios.get('http://localhost:8080/api/board/post/list');
  },

  // 게시글 상세보기
  getArticle(id) {
    return axios.get(`${BASE_URL2}/post/view/${id}`);
  },

  // postArticle(id, title, body) {
  //   console.log('3333');
  //   return axios.post(`${BASE_URL2}insert/${id}`, {
  //     id,
  //     title,
  //     body,
  //   });
  // },

  // 게시글 수정
  patchArticle(boardId, title, content, imageLists) {
    console.log('imageLists: ', imageLists);
    return axios.patch(`${BASE_URL2}update/${boardId}`, {
      boardId,
      title,
      content,
      imageLists,
    });
  },

  // 게시글 삭제 : 구현 안됨
  delete(id) {
    return axios.delete(`${BASE_URL2}posts/${id}`);
  },

  ajaxImageUploader(formData) {
    return axios.post(`${BASE_URL2}/upload-image`, formData);
    // return axios.post('http://localhost:8080/api/board/upload-image', formData);
  },
};
