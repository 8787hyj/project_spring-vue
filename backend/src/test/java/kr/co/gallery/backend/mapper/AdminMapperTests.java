package kr.co.gallery.backend.mapper;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.gallery.backend.dao.BoardDao;
import kr.co.gallery.backend.model.AttachImageVO;
import kr.co.gallery.backend.model.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
class AdminMapperTests {

	@Autowired
	private BoardDao mapper;
	
//	@Test
//	public void bookEnrollTest() throws Exception{
//		
//		BoardVO board = new BoardVO();
//		
//		board.setBoardno(1);
//		board.setContent("컨텐츠1");
//		board.setTitle("타이틀1");
//		board.setWriter("쓰기1");
//		
//		System.out.println("Before BoardVO : " + board);
//		
//		mapper.imageEnroll(board);
//
//		System.out.println("After BoardVO : " + board);
//	}

	@Test
	public void imageEnrollTest() throws Exception {
		
//		AttachImageVO vo = new AttachImageVO();
		
//		vo.setBoardId(137);
//		vo.setFileName("test");
//		vo.setUploadPath("test");
//		vo.setUuid("test2");
		
//		mapper.imageEnroll(vo);
		mapper.list();
		
	}
}
