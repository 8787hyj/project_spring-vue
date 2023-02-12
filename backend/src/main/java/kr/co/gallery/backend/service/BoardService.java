package kr.co.gallery.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gallery.backend.dao.BoardDao;
import kr.co.gallery.backend.model.AttachFileVO;
import kr.co.gallery.backend.model.AttachImageVO;
import kr.co.gallery.backend.model.BoardVO;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardmapper;
	
	public void boardEnroll(BoardVO board) {

		boardmapper.boardEnroll(board);
		
		/* 이미지가 없는 경우 실행 종료 */
//		if(board.getImageList() == null || board.getImageList().size() <= 0) {
//			return;
//		}
		
		if (board.getImageList() != null) {
			
			for(AttachImageVO attach : board.getImageList()) {
				attach.setBoardid(board.getBoardid());
				boardmapper.imageEnroll(attach);
			}
		}
		if (board.getFileList() != null) {
			
			for(AttachFileVO attach : board.getFileList()) {
				attach.setBoardid(board.getBoardid());
				boardmapper.fileEnroll(attach);
			}
		}
	}

	public BoardVO openPostView(Integer id) {
		BoardVO result = boardmapper.openPostView(id);
		return result;
	}

	public List<BoardVO> list() {
		List<BoardVO> list = boardmapper.list();
		return list;
	}

	public void boardUpdate(BoardVO board) {

		boardmapper.boardUpdate(board);
		System.out.println(board.toString());

		for (AttachImageVO attach : board.getImageList()) {
			attach.setBoardid(board.getBoardid());
			if (attach.getImageId() == null) {
				boardmapper.imageEnroll(attach);
				return;
			} else {
				boardmapper.imageUpdate(attach);
			}
		}
		for (AttachFileVO attach : board.getFileList()) {
			attach.setBoardid(board.getBoardid());
			if (attach.getFileId() == null) {
				boardmapper.fileEnroll(attach);
				return;
			} else {
				boardmapper.fileUpdate(attach);
			}
		}
	}

}
